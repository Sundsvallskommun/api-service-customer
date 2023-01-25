package se.sundsvall.customer.api;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import se.sundsvall.customer.Application;
import se.sundsvall.customer.api.model.Customer;
import se.sundsvall.customer.service.CustomerService;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class CustomerResourceTest {

	@MockBean
	private CustomerService customerServiceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getCustomerByPartyId() {

		// Parameters
		final var partyId = UUID.randomUUID().toString();

		// Mock
		when(customerServiceMock.getCustomer(partyId)).thenReturn(Customer.create());

		// Call
		webTestClient.get().uri("/customers/{partyId}", partyId)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody()
			.jsonPath("$").isMap()
			.jsonPath("$").isEmpty();

		// Verification
		verify(customerServiceMock).getCustomer(partyId);
	}
}
