package se.sundsvall.customer.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.code.beanmatchers.BeanMatchers;

class CustomerRelationTest {
	@BeforeAll
	static void setup() {
		BeanMatchers.registerValueGenerator(() -> LocalDate.now().plusDays(new Random().nextInt()), LocalDate.class);
	}

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
		final var active = true;
		final var moveInDate = LocalDate.now();

		final var customerRelation = CustomerRelation.create()
			.withCustomerNumber(customerId)
			.withOrganizationName(organizationName)
			.withOrganizationNumber(organizationNumber)
			.withActive(active)
			.withMoveInDate(moveInDate);

		assertThat(customerRelation).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(customerRelation.getCustomerNumber()).isEqualTo(customerId);
		assertThat(customerRelation.getOrganizationName()).isEqualTo(organizationName);
		assertThat(customerRelation.getOrganizationNumber()).isEqualTo(organizationNumber);
		assertThat(customerRelation.isActive()).isEqualTo(active);
		assertThat(customerRelation.getMoveInDate()).isEqualTo(moveInDate);
	}

	@Test
	void hasNoDirtOnCreatedBean() {
		assertThat(CustomerRelation.create()).hasAllNullFieldsOrPropertiesExcept("active")
			.hasFieldOrPropertyWithValue("active", false);

	}
}
