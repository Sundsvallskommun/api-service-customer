package se.sundsvall.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.sundsvall.customer.api.model.CustomerType.ENTERPRISE;
import static se.sundsvall.customer.api.model.CustomerType.PRIVATE;

import generated.se.sundsvall.datawarehousereader.CustomerDetails;
import generated.se.sundsvall.datawarehousereader.CustomerDetailsResponse;
import generated.se.sundsvall.datawarehousereader.CustomerEngagement;
import generated.se.sundsvall.datawarehousereader.CustomerEngagementResponse;
import generated.se.sundsvall.datawarehousereader.Direction;
import generated.se.sundsvall.datawarehousereader.PagingAndSortingMetaData;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.zalando.problem.ThrowableProblem;
import se.sundsvall.customer.api.model.CustomerRelation;
import se.sundsvall.customer.api.model.CustomerType;

class CustomerMapperTest {

	@Test
	void toEnterPriseCustomer() {

		// Parameters
		final var customerEngagementResponse = new CustomerEngagementResponse()
			.customerEngagements(List.of(
				new CustomerEngagement()
					.customerNumber("111")
					.customerType(generated.se.sundsvall.datawarehousereader.CustomerType.ENTERPRISE)
					.organizationName("organizationName-1")
					.organizationNumber("organizationNumber-1"),
				new CustomerEngagement()
					.customerNumber("222")
					.customerType(generated.se.sundsvall.datawarehousereader.CustomerType.ENTERPRISE)
					.organizationName("organizationName-2")
					.organizationNumber("organizationNumber-2"),
				new CustomerEngagement()
					.customerNumber("333")
					.customerType(generated.se.sundsvall.datawarehousereader.CustomerType.ENTERPRISE)
					.organizationName("organizationName-3")
					.organizationNumber("organizationNumber-3")));

		// Call
		final var result = CustomerMapper.toCustomer(customerEngagementResponse);

		// Verification
		assertThat(result).isNotNull();
		assertThat(result.getCustomerType()).isEqualTo(CustomerType.ENTERPRISE);
		assertThat(result.getCustomerRelations()).isNotNull().hasSize(3).containsExactly(
			CustomerRelation.create()
				.withCustomerNumber("111")
				.withOrganizationName("organizationName-1")
				.withOrganizationNumber("organizationNumber-1"),
			CustomerRelation.create()
				.withCustomerNumber("222")
				.withOrganizationName("organizationName-2")
				.withOrganizationNumber("organizationNumber-2"),
			CustomerRelation.create()
				.withCustomerNumber("333")
				.withOrganizationName("organizationName-3")
				.withOrganizationNumber("organizationNumber-3"));
	}

	@Test
	void toPrivateCustomer() {

		// Parameters
		final var customerEngagementResponse = new CustomerEngagementResponse()
			.customerEngagements(List.of(
				new CustomerEngagement()
					.customerNumber("111")
					.customerType(generated.se.sundsvall.datawarehousereader.CustomerType.PRIVATE)
					.organizationName("organizationName-1")
					.organizationNumber("organizationNumber-1"),
				new CustomerEngagement()
					.customerNumber("222")
					.customerType(generated.se.sundsvall.datawarehousereader.CustomerType.PRIVATE)
					.organizationName("organizationName-2")
					.organizationNumber("organizationNumber-2"),
				new CustomerEngagement()
					.customerNumber("333")
					.customerType(generated.se.sundsvall.datawarehousereader.CustomerType.PRIVATE)
					.organizationName("organizationName-3")
					.organizationNumber("organizationNumber-3")));

		// Call
		final var result = CustomerMapper.toCustomer(customerEngagementResponse);

		// Verification
		assertThat(result).isNotNull();
		assertThat(result.getCustomerType()).isEqualTo(CustomerType.PRIVATE);
		assertThat(result.getCustomerRelations()).isNotNull().hasSize(3).containsExactly(
			CustomerRelation.create()
				.withCustomerNumber("111")
				.withOrganizationName("organizationName-1")
				.withOrganizationNumber("organizationNumber-1"),
			CustomerRelation.create()
				.withCustomerNumber("222")
				.withOrganizationName("organizationName-2")
				.withOrganizationNumber("organizationNumber-2"),
			CustomerRelation.create()
				.withCustomerNumber("333")
				.withOrganizationName("organizationName-3")
				.withOrganizationNumber("organizationNumber-3"));
	}

	@Test
	void toCustomerWhenCustomerEngagementsIsNull() {

		// Parameters
		final var customerEngagementResponse = new CustomerEngagementResponse();

		// Call
		final var exception = assertThrows(ThrowableProblem.class, () -> CustomerMapper.toCustomer(customerEngagementResponse));

		// Verification
		assertThat(exception).isNotNull().hasMessage("Not Found: No customer matched search criteria!");
	}

	@Test
	void toCustomerWhenCustomerEngagementsIsEmpty() {

		// Parameters
		final var customerEngagementResponse = new CustomerEngagementResponse().customerEngagements(Collections.emptyList());

		// Call
		final var exception = assertThrows(ThrowableProblem.class, () -> CustomerMapper.toCustomer(customerEngagementResponse));

		// Verification
		assertThat(exception).isNotNull().hasMessage("Not Found: No customer matched search criteria!");
	}

	@Test
	void toCustomerWhenCustomerTypeIsNotFound() {

		// Parameters
		final var customerEngagementResponse = new CustomerEngagementResponse()
			.customerEngagements(List.of(
				new CustomerEngagement()
					.customerNumber("111")
					.customerType(null))); // Invalid customerType

		// Call
		final var exception = assertThrows(ThrowableProblem.class, () -> CustomerMapper.toCustomer(customerEngagementResponse));

		// Verification
		assertThat(exception).isNotNull().hasMessage("Not Found: No valid customerType was found!");
	}

	@Test
	void toCustomerType() {
		assertThat(CustomerMapper.toCustomerType(generated.se.sundsvall.datawarehousereader.CustomerType.ENTERPRISE)).isEqualTo(ENTERPRISE);
		assertThat(CustomerMapper.toCustomerType(generated.se.sundsvall.datawarehousereader.CustomerType.PRIVATE)).isEqualTo(PRIVATE);
	}

	@Test
	void toCustomerTypeWhenNull() {
		final var e = assertThrows(IllegalArgumentException.class, () -> CustomerMapper.toCustomerType(null));
		assertThat(e).hasMessage("DataWarehouseReader enum customerType was null!");
	}

	@Test
	void toCustomerDetailsResponse() {

		final var dwrResponse = createDwrResponse();
		final var bean = CustomerMapper.toCustomerDetailsResponse(dwrResponse);

		assertThat(bean.getCustomerDetails()).isNotNull().hasSize(1).satisfiesExactly(d -> {
			final var dwrDetails = dwrResponse.getCustomerDetails().getFirst();

			assertThat(d.getCareOf()).isEqualTo(dwrDetails.getCareOf());
			assertThat(d.getCity()).isEqualTo(dwrDetails.getCity());
			assertThat(d.getCustomerCategoryDescription()).isEqualTo(dwrDetails.getCustomerCategoryDescription());
			assertThat(d.getCustomerCategoryID()).isEqualTo(dwrDetails.getCustomerCategoryID());
			assertThat(d.getCustomerEngagementOrgId()).isEqualTo(dwrDetails.getCustomerEngagementOrgId());
			assertThat(d.getCustomerEngagementOrgName()).isEqualTo(dwrDetails.getCustomerEngagementOrgName());
			assertThat(d.getCustomerName()).isEqualTo(dwrDetails.getCustomerName());
			assertThat(d.getCustomerNumber()).isEqualTo(dwrDetails.getCustomerNumber());
			assertThat(d.getEmails()).isEqualTo(dwrDetails.getEmails());
			assertThat(d.getMoveInDate()).isEqualTo(dwrDetails.getMoveInDate());
			assertThat(d.getPartyId()).isEqualTo(dwrDetails.getPartyId());
			assertThat(d.getPhoneNumbers()).isEqualTo(dwrDetails.getPhoneNumbers());
			assertThat(d.getPostalCode()).isEqualTo(dwrDetails.getPostalCode());
			assertThat(d.getStreet()).isEqualTo(dwrDetails.getStreet());
		});

		assertThat(bean.getMetaData()).isNotNull().satisfies(m -> {
			final var dwrMeta = dwrResponse.getMeta();

			assertThat(m.getCount()).isEqualTo(dwrMeta.getCount());
			assertThat(m.getLimit()).isEqualTo(dwrMeta.getLimit());
			assertThat(m.getPage()).isEqualTo(dwrMeta.getPage());
			assertThat(m.getSortBy()).isEqualTo(dwrMeta.getSortBy());
			assertThat(m.getSortDirection()).hasToString(dwrMeta.getSortDirection().toString());
			assertThat(m.getTotalPages()).isEqualTo(dwrMeta.getTotalPages());
			assertThat(m.getTotalRecords()).isEqualTo(dwrMeta.getTotalRecords());
		});
	}

	@Test
	void toCustomerDetailsResponseFromNull() {
		assertThat(CustomerMapper.toCustomerDetailsResponse(null)).isNull();
	}

	@Test
	void toCustomerDetailsResponseWhenNullDetailsAndNullMeta() {
		final var bean = CustomerMapper.toCustomerDetailsResponse(new CustomerDetailsResponse());

		assertThat(bean.getCustomerDetails()).isEmpty();
		assertThat(bean.getMetaData()).isNull();
	}

	@Test
	void toCustomerDetailsResponseWhenMetaAndDetailsAreEmpty() {
		final var bean = CustomerMapper.toCustomerDetailsResponse(new CustomerDetailsResponse()
			.customerDetails(List.of(new CustomerDetails()))
			.meta(new PagingAndSortingMetaData()));

		assertThat(bean.getCustomerDetails()).isNotNull().hasSize(1).satisfiesExactly(d -> {
			assertThat(d.getCareOf()).isNull();
			assertThat(d.getCity()).isNull();
			assertThat(d.getCustomerCategoryDescription()).isNull();
			assertThat(d.getCustomerCategoryID()).isZero();
			assertThat(d.getCustomerEngagementOrgId()).isNull();
			assertThat(d.getCustomerEngagementOrgName()).isNull();
			assertThat(d.getCustomerName()).isNull();
			assertThat(d.getCustomerNumber()).isNull();
			assertThat(d.getEmails()).isEmpty();
			assertThat(d.getMoveInDate()).isNull();
			assertThat(d.getPartyId()).isNull();
			assertThat(d.getPhoneNumbers()).isEmpty();
			assertThat(d.getPostalCode()).isNull();
			assertThat(d.getStreet()).isNull();
		});
		assertThat(bean.getMetaData()).isNotNull().satisfies(m -> {
			assertThat(m.getCount()).isZero();
			assertThat(m.getLimit()).isZero();
			assertThat(m.getPage()).isZero();
			assertThat(m.getSortBy()).isNullOrEmpty();
			assertThat(m.getSortDirection()).isNull();
			assertThat(m.getTotalPages()).isZero();
			assertThat(m.getTotalRecords()).isZero();
		});
	}

	private static final CustomerDetailsResponse createDwrResponse() {
		final var customerDetail = new CustomerDetails()
			.active(true)
			.careOf("careOf")
			.city("city")
			.customerCategoryDescription("customerCategoryDescription")
			.customerCategoryID(123)
			.customerChangedFlg(true)
			.customerEngagementOrgId("customerEngagementOrgId")
			.customerEngagementOrgName("customerEngagementOrgName")
			.customerName("customerName")
			.customerNumber("customerNumber")
			.emails(List.of("email1", "email2"))
			.installedChangedFlg(true)
			.moveInDate(LocalDate.now())
			.partyId("partyId")
			.phoneNumbers(List.of("phoneNumber1", "phoneNumber2"))
			.postalCode("postalCode")
			.street("street");

		final var customerDetails = List.of(customerDetail);
		final var meta = new PagingAndSortingMetaData()
			.count(1)
			.limit(2)
			.page(3)
			.sortBy(List.of("a", "b", "c"))
			.sortDirection(Direction.ASC)
			.totalPages(4)
			.totalRecords(5L);

		return new CustomerDetailsResponse()
			.customerDetails(customerDetails)
			.meta(meta);
	}
}
