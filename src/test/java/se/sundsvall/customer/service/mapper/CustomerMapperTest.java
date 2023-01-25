package se.sundsvall.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.sundsvall.customer.api.model.CustomerType.ENTERPRISE;
import static se.sundsvall.customer.api.model.CustomerType.PRIVATE;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.zalando.problem.ThrowableProblem;

import generated.se.sundsvall.datawarehousereader.CustomerEngagement;
import generated.se.sundsvall.datawarehousereader.CustomerEngagementResponse;
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
}
