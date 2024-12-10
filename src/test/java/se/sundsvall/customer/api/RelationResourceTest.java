package se.sundsvall.customer.api;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.sundsvall.customer.Application;
import se.sundsvall.customer.api.model.Customer;
import se.sundsvall.customer.service.CustomerService;

@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("junit")
class RelationResourceTest {

	private static final String PATH = "/{municipalityId}/relations/{partyId}";

	@MockitoBean
	private CustomerService customerServiceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getRelationByPartyId() {

		// Arrange
		final var municipalityId = "2281";
		final var partyId = randomUUID().toString();

		when(customerServiceMock.getCustomer(municipalityId, partyId)).thenReturn(Customer.create());

		// Act
		final var response = webTestClient.get().uri(PATH, municipalityId, partyId)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(Customer.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response).isNotNull();

		verify(customerServiceMock).getCustomer(municipalityId, partyId);
	}
}
