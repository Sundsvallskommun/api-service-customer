package se.sundsvall.customer.service;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.sundsvall.customer.service.CustomerService.DATE_TIME_FORMAT;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zalando.problem.ThrowableProblem;

import se.sundsvall.customer.api.model.CustomerDetailsRequest;
import se.sundsvall.customer.integration.datawarehousereader.DataWarehouseReaderClient;

import generated.se.sundsvall.datawarehousereader.CustomerDetails;
import generated.se.sundsvall.datawarehousereader.CustomerDetailsResponse;
import generated.se.sundsvall.datawarehousereader.CustomerEngagement;
import generated.se.sundsvall.datawarehousereader.CustomerEngagementResponse;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	@Mock
	private DataWarehouseReaderClient dataWarehouseReaderClientMock;

	@InjectMocks
	private CustomerService customerService;

	@Test
	void getCustomer() {
		// Parameters
		final var partyId = UUID.randomUUID().toString();

		// Mock
		when(dataWarehouseReaderClientMock.getCustomerEngagement(partyId)).thenReturn(new CustomerEngagementResponse()
			.customerEngagements(List.of(new CustomerEngagement()
				.customerNumber("111")
				.customerType(generated.se.sundsvall.datawarehousereader.CustomerType.ENTERPRISE)
				.organizationName("organizationName-1")
				.organizationNumber("organizationNumber-1"))));

		// Call
		final var result = customerService.getCustomer(partyId);

		// Verification
		assertThat(result).isNotNull();
		verify(dataWarehouseReaderClientMock).getCustomerEngagement(partyId);
	}

	@Test
	void getCustomerWhenNotFound() {
		// Parameters
		final var partyId = UUID.randomUUID().toString();

		// Mock
		when(dataWarehouseReaderClientMock.getCustomerEngagement(partyId)).thenReturn(new CustomerEngagementResponse()
			.customerEngagements(emptyList()));

		// Call
		final var exception = assertThrows(ThrowableProblem.class, () -> customerService.getCustomer(partyId));

		// Verification
		assertThat(exception).isNotNull().hasMessage("Not Found: No customer matched search criteria!");
		verify(dataWarehouseReaderClientMock).getCustomerEngagement(partyId);
	}

	@Test
	void getCustomerDetailsForPartyId() {
		// Parameters
		final var partyId = List.of(UUID.randomUUID().toString());
		final var fromDateTime = OffsetDateTime.now();
		final var request = new CustomerDetailsRequest()
			.withPartyId(partyId)
			.withFromDateTime(fromDateTime);

		final var fromDateTimeAsString = DATE_TIME_FORMAT.format(fromDateTime);

		// Mock
		when(dataWarehouseReaderClientMock.getCustomerDetails(partyId, null, fromDateTimeAsString))
			.thenReturn(new CustomerDetailsResponse()
				.customerDetails(List.of(new CustomerDetails()
					.partyId(partyId.get(0))
					.customerNumber("customerNumber")
					.customerName("customerName")
					.customerCategoryID(1)
					.street("street")
					.postalCode("postalCode")
					.city("city")
					.careOf("careOf-1")
					.phoneNumbers(List.of("phoneNumbers-1", "phoneNumbers-2"))
					.emails(List.of("emails-1", "emails-2")))));

		// Call
		final var result = customerService.getCustomerDetails(request);

		// Verification
		assertThat(result).isNotNull();
		verify(dataWarehouseReaderClientMock).getCustomerDetails(partyId, null, fromDateTimeAsString);
	}

	@Test
	void getCustomerDetailsForCustomerEngagementOrgId() {
		// Parameters
		final var orgId = "1234567890";
		final var fromDateTime = OffsetDateTime.now();
		final var request = new CustomerDetailsRequest()
			.withCustomerEngagementOrgId(orgId)
			.withFromDateTime(fromDateTime);

		final var fromDateTimeAsString = DATE_TIME_FORMAT.format(fromDateTime);

		// Mock
		when(dataWarehouseReaderClientMock.getCustomerDetails(null, orgId, fromDateTimeAsString))
			.thenReturn(new CustomerDetailsResponse()
				.customerDetails(List.of(new CustomerDetails()
					.partyId("somePartyId")
					.customerNumber("customerNumber")
					.customerName("customerName")
					.customerCategoryID(1)
					.street("street")
					.postalCode("postalCode")
					.city("city")
					.careOf("careOf-1")
					.phoneNumbers(List.of("phoneNumbers-1", "phoneNumbers-2"))
					.emails(List.of("emails-1", "emails-2")))));

		// Call
		final var result = customerService.getCustomerDetails(request);

		// Verification
		assertThat(result).isNotNull();
		verify(dataWarehouseReaderClientMock).getCustomerDetails(null, orgId, fromDateTimeAsString);
	}

	@Test
	void getCustomerDetailsForPartyIdAndCustomerEngagementOrgId() {
		// Parameters
		final var partyId = List.of(UUID.randomUUID().toString());
		final var orgId = "1234567890";
		final var fromDateTime = OffsetDateTime.now();
		final var request = new CustomerDetailsRequest()
			.withPartyId(partyId)
			.withCustomerEngagementOrgId(orgId)
			.withFromDateTime(fromDateTime);

		final var fromDateTimeAsString = DATE_TIME_FORMAT.format(fromDateTime);

		// Mock
		when(dataWarehouseReaderClientMock.getCustomerDetails(partyId, orgId, fromDateTimeAsString))
			.thenReturn(new CustomerDetailsResponse()
				.customerDetails(List.of(new CustomerDetails()
					.partyId("somePartyId")
					.customerNumber("customerNumber")
					.customerName("customerName")
					.customerCategoryID(1)
					.street("street")
					.postalCode("postalCode")
					.city("city")
					.careOf("careOf-1")
					.phoneNumbers(List.of("phoneNumbers-1", "phoneNumbers-2"))
					.emails(List.of("emails-1", "emails-2")))));

		// Call
		final var result = customerService.getCustomerDetails(request);

		// Verification
		assertThat(result).isNotNull();
		verify(dataWarehouseReaderClientMock).getCustomerDetails(partyId, orgId, fromDateTimeAsString);
	}

	@Test
	void getCustomerDetailsWhenNotFound() {
		// Parameters
		final var partyId = List.of(UUID.randomUUID().toString());
		final var fromDateTime = OffsetDateTime.now();
		final var request = new CustomerDetailsRequest()
			.withPartyId(partyId)
			.withFromDateTime(fromDateTime);

		final var fromDateTimeAsString = DATE_TIME_FORMAT.format(fromDateTime);

		// Mock
		when(dataWarehouseReaderClientMock.getCustomerDetails(partyId, null, fromDateTimeAsString)).thenReturn(new CustomerDetailsResponse()
			.customerDetails(emptyList()));

		// Call
		final var result = customerService.getCustomerDetails(request);

		// Verification
		assertThat(result.getCustomerDetails()).isEmpty();
		verify(dataWarehouseReaderClientMock).getCustomerDetails(partyId, null, fromDateTimeAsString);
	}
}
