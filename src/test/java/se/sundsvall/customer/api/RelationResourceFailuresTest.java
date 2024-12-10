package se.sundsvall.customer.api;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;
import static org.zalando.problem.Status.BAD_REQUEST;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;
import se.sundsvall.customer.Application;
import se.sundsvall.customer.service.CustomerService;

@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("junit")
class RelationResourceFailuresTest {

	private static final String PATH = "/{municipalityId}/relations/{partyId}";

	@MockitoBean
	private CustomerService customerServiceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getRelationPartyIdInvalidPartyId() {

		// Arrange
		final var municipalityId = "2281";
		final var partyId = "invalid";

		// Act
		final var response = webTestClient.get().uri(PATH, municipalityId, partyId)
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("getRelationByPartyId.partyId", "not a valid UUID"));

		// Verifications
		verifyNoInteractions(customerServiceMock);
	}

	@Test
	void getRelationPartyIdInvalidMunicipalityId() {

		// Arrange
		final var municipalityId = "invalid";
		final var partyId = randomUUID().toString();

		// Act
		final var response = webTestClient.get().uri(PATH, municipalityId, partyId)
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("getRelationByPartyId.municipalityId", "not a valid municipality ID"));

		// Verifications
		verifyNoInteractions(customerServiceMock);
	}
}
