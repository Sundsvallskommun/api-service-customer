package se.sundsvall.customer.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.zalando.problem.Problem;
import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;
import se.sundsvall.customer.Application;
import se.sundsvall.customer.api.model.CustomerDetailsRequest;
import se.sundsvall.customer.service.CustomerService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.apache.hc.core5.http.HttpHeaders.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.zalando.problem.Status.BAD_REQUEST;

@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("junit")
class DetailsResourceFailureTest {

	@MockBean
	private CustomerService customerServiceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getDetailsInvalidOffsetDateTime() {
		// Arrange
		final var partyId = UUID.randomUUID().toString();
		final var offsetDateTime = "not-valid";
		final var jsonBody = format("{\"partyId\": [\"%s\"], \"fromDateTime\": \"%s\"}", partyId, offsetDateTime);

		// Act
		final var response = webTestClient.post().uri("/details")
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.bodyValue(jsonBody)
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON)
			.expectBody(Problem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response).isNotNull();
		assertThat(response.getTitle()).isEqualTo("Bad Request");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getDetail()).contains(
			"JSON parse error: Cannot deserialize value of type `java.time.OffsetDateTime` from String \"not-valid\": Failed to deserialize java.time.OffsetDateTime: (java.time.format.DateTimeParseException) Text 'not-valid' could not be parsed at index 0");

		// Verifications
		verifyNoInteractions(customerServiceMock);
	}

	@ParameterizedTest(name = "when {0}")
	@MethodSource("invalidRequestProvider")
	void getDetailsWithInvalidRequests(final CustomerDetailsRequestForTest request) {
		final var expectedResponseBodyType = request.isConstraintViolation ? ConstraintViolationProblem.class : Problem.class;
		final var response = webTestClient.post().uri("/details")
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(fromValue(request))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON)
			.expectBody(expectedResponseBodyType)
			.returnResult()
			.getResponseBody();

		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);

		if (response instanceof ConstraintViolationProblem constraintViolationProblem) {
			assertThat(constraintViolationProblem.getTitle()).isEqualTo("Constraint Violation");

			if (!isEmpty(request.getPartyId())) {
				// We have constraint violations on partyId
				assertThat(constraintViolationProblem.getViolations())
					.extracting(Violation::getField, Violation::getMessage)
					.containsExactly(tuple("partyId[0]", "not a valid UUID"));
			} else {
				// We have constraint violations on customerEngagementOrgId
				assertThat(constraintViolationProblem.getViolations())
					.extracting(Violation::getField, Violation::getMessage)
					.containsExactly(tuple("customerEngagementOrgId", "must match the regular expression ^([1235789][\\d][2-9]\\d{7})$"));
			}
		} else {
			assertThat(response.getTitle()).isEqualTo("Bad Request");
			assertThat(response.getDetail()).isEqualTo("'partyId' or 'customerEngagementOrgId' must be provided");
		}

		verifyNoInteractions(customerServiceMock);
	}

	private static Stream<CustomerDetailsRequest> invalidRequestProvider() {

		return Stream.of(
			new CustomerDetailsRequestForTest("neither partyId or customerEngagementOrgId is set"),
			new CustomerDetailsRequestForTest("partyId is set but empty")
				.withPartyId(List.of()),
			new CustomerDetailsRequestForTest("customerEngagementOrgId is set but empty")
				.asConstraintViolation()
				.withCustomerEngagementOrgId(""),
			new CustomerDetailsRequestForTest("partyId is set but invalid")
				.asConstraintViolation()
				.withPartyId(List.of("invalid-party-id")),
			new CustomerDetailsRequestForTest("customerEngagementOrgId is set but invalid")
				.asConstraintViolation()
				.withCustomerEngagementOrgId("invalid-org-id")
		);
	}

	/*
	 * Simple extension of {@code CustomerDetailsRequest} used only to be able to have meaningful
	 * names for parameterized test executions and to distinguish between "regular" and constraint
	 * violation-related bad requests
	 */
	static class CustomerDetailsRequestForTest extends CustomerDetailsRequest {

		final String description;
		boolean isConstraintViolation;

		CustomerDetailsRequestForTest(final String description) {
			this.description = description;
		}

		CustomerDetailsRequestForTest asConstraintViolation() {
			this.isConstraintViolation = true;
			return this;
		}

		@Override
		public String toString() {
			return description;
		}
	}
}
