package se.sundsvall.customer.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.sundsvall.dept44.models.api.paging.PagingAndSortingMetaData;

class CustomerDetailsResponseTest {

	@Test
	void isValidBean() {
		assertThat(CustomerDetailsResponse.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals()));
	}

	@Test
	void hasValidBuilderMethods() {

		final var customerDetails = List.of(CustomerDetails.create());
		final var metaData = PagingAndSortingMetaData.create();

		final var bean = CustomerDetailsResponse.create()
			.withCustomerDetails(customerDetails)
			.withMetadata(metaData);

		assertThat(bean).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(bean.getCustomerDetails()).isEqualTo(customerDetails);
		assertThat(bean.getMetaData()).isEqualTo(metaData);
	}

	@Test
	void hasNoDirtOnCreatedBean() {
		assertThat(new CustomerDetailsResponse()).hasAllNullFieldsOrProperties();
		assertThat(CustomerDetailsResponse.create()).hasAllNullFieldsOrProperties();
	}
}
