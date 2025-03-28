package se.sundsvall.customer.api;

import static org.apache.hc.core5.http.HttpHeaders.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.sundsvall.customer.Application;
import se.sundsvall.customer.api.model.Customer;
import se.sundsvall.customer.api.model.CustomerDetails;
import se.sundsvall.customer.api.model.CustomerDetailsRequest;
import se.sundsvall.customer.api.model.CustomerDetailsResponse;
import se.sundsvall.customer.service.CustomerService;

@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("junit")
class DetailsResourceTest {

	private static final String PATH = "/{municipalityId}/details";

	@MockitoBean
	private CustomerService customerServiceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getDetailsByCustomerEngagementOrgIdAndFromDate() {

		// Arrange
		final var municipalityId = "2281";
		final var request = new CustomerDetailsRequest()
			.withCustomerEngagementOrgId("1234567890")
			.withFromDateTime(OffsetDateTime.now());

		when(customerServiceMock.getCustomerDetails(municipalityId, request)).thenReturn(new CustomerDetailsResponse());

		// Act
		final var response = webTestClient.post().uri(PATH, municipalityId)
			.contentType(APPLICATION_JSON)
			.body(fromValue(request))
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(Customer.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response).isNotNull();

		verify(customerServiceMock).getCustomerDetails(municipalityId, request);
	}

	@Test
	void getDetailsByPartyIdAndCustomerEngagementOrgIdAndFromDate() {

		// Arrange
		final var municipalityId = "2281";
		final var request = new CustomerDetailsRequest()
			.withPartyId(List.of(UUID.randomUUID().toString()))
			.withCustomerEngagementOrgId("1234567890")
			.withFromDateTime(OffsetDateTime.now());
		final var customerDetailsResponse = CustomerDetailsResponse.create()
			.withCustomerDetails(List.of(CustomerDetails.create().withPartyId("somePartyId")));

		when(customerServiceMock.getCustomerDetails(municipalityId, request)).thenReturn(customerDetailsResponse);

		// Act
		final var response = webTestClient.post().uri(PATH, municipalityId)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(fromValue(request))
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(CustomerDetailsResponse.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response).isNotNull();

		verify(customerServiceMock).getCustomerDetails(municipalityId, request);
	}

	@Test
	void getDetailsByPartyIdAndCustomerEngagementOrgId() {

		// Arrange
		final var municipalityId = "2281";
		final var request = new CustomerDetailsRequest()
			.withPartyId(List.of(UUID.randomUUID().toString()))
			.withCustomerEngagementOrgId("1234567890");
		final var customerDetailsResponse = CustomerDetailsResponse.create()
			.withCustomerDetails(List.of(CustomerDetails.create().withPartyId("somePartyId")));

		when(customerServiceMock.getCustomerDetails(municipalityId, request)).thenReturn(customerDetailsResponse);

		// Act
		final var response = webTestClient.post().uri(PATH, municipalityId)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(fromValue(request))
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(CustomerDetailsResponse.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response).isNotNull();

		verify(customerServiceMock).getCustomerDetails(municipalityId, request);
	}
}
