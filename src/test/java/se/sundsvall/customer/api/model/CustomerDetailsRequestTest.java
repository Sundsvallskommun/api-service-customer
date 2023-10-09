package se.sundsvall.customer.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;

import com.google.code.beanmatchers.BeanMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CustomerDetailsRequestTest {

	@BeforeAll
	static void setup() {
		BeanMatchers.registerValueGenerator(() -> OffsetDateTime.now().plusDays(new Random().nextInt()), OffsetDateTime.class);
	}

	@Test
	void isValidBean() {
		assertThat(CustomerDetailsRequest.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals()));
	}

	@Test
	void hasValidBuilderMethods() {
		final var partyId = "somePartyId";
		final var customerEngagementOrgId = "someCustomerEngagementOrgId";
		final var fromDateTime = OffsetDateTime.now().minusYears(5);

		final var request = new CustomerDetailsRequest()
			.withPartyId(List.of(partyId))
			.withCustomerEngagementOrgId(customerEngagementOrgId)
			.withFromDateTime(fromDateTime);

		assertThat(request).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(request.getPartyId()).containsExactly(partyId);
		assertThat(request.getCustomerEngagementOrgId()).isEqualTo(customerEngagementOrgId);
		assertThat(request.getFromDateTime()).isEqualTo(fromDateTime);
	}

	@Test
	void hasNoDirtOnCreatedBean() {
		assertThat(new CustomerDetailsRequest()).hasAllNullFieldsOrProperties();
	}
}
