package se.sundsvall.customer.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class CustomerRelationTest {

	@Test
	void isValidBean() {
		assertThat(CustomerRelation.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void hasValidBuilderMethods() {

		final var customerId = "customerId";
		final var organizationName = "organizationName";
		final var organizationNumber = "organizationNumber";

		final var customerRelation = CustomerRelation.create()
			.withCustomerNumber(customerId)
			.withOrganizationName(organizationName)
			.withOrganizationNumber(organizationNumber);

		assertThat(customerRelation).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(customerRelation.getCustomerNumber()).isEqualTo(customerId);
		assertThat(customerRelation.getOrganizationName()).isEqualTo(organizationName);
		assertThat(customerRelation.getOrganizationNumber()).isEqualTo(organizationNumber);
	}

	@Test
	void hasNoDirtOnCreatedBean() {
		assertThat(CustomerRelation.create()).hasAllNullFieldsOrProperties();
	}
}
