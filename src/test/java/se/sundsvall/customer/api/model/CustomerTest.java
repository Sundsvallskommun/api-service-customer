package se.sundsvall.customer.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class CustomerTest {

	@Test
	void isValidBean() {
		assertThat(Customer.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void hasValidBuilderMethods() {

		final var customerType = CustomerType.PRIVATE;
		final var customerRelations = List.of(
			CustomerRelation.create()
				.withCustomerNumber("customerNumber1")
				.withOrganizationName("organizationName1")
				.withOrganizationNumber("organizationNumber1"),
			CustomerRelation.create()
				.withCustomerNumber("customerNumber1")
				.withOrganizationName("organizationName1")
				.withOrganizationNumber("organizationNumber1"));

		final var customer = Customer.create()
			.withCustomerRelations(customerRelations)
			.withCustomerType(customerType);

		assertThat(customer).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(customer.getCustomerRelations()).isEqualTo(customerRelations);
		assertThat(customer.getCustomerType()).isEqualTo(customerType);
	}

	@Test
	void hasNoDirtOnCreatedBean() {
		assertThat(Customer.create()).hasAllNullFieldsOrProperties();
	}
}
