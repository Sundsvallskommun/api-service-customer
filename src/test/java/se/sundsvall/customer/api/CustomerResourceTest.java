package se.sundsvall.customer.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
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

@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("junit")
class CustomerResourceTest {

	@MockBean
	private CustomerService customerServiceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getCustomerByPartyId() {

		// Arrange
		final var partyId = UUID.randomUUID().toString();

		when(customerServiceMock.getCustomer(partyId)).thenReturn(Customer.create());

		// Act
		final var response = webTestClient.get().uri("/customers/{partyId}", partyId)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(Customer.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response).isNotNull();

		verify(customerServiceMock).getCustomer(partyId);
	}
}
