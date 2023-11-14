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

import generated.se.sundsvall.datawarehousereader.Direction;

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
		request.setSortBy(List.of("sort1", "sort2"));

		assertThat(request).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(request.getPartyId()).containsExactly(partyId);
		assertThat(request.getCustomerEngagementOrgId()).isEqualTo(customerEngagementOrgId);
		assertThat(request.getFromDateTime()).isEqualTo(fromDateTime);
		assertThat(request.getPage()).isEqualTo(1);
		assertThat(request.getLimit()).isEqualTo(100);
		assertThat(request.getSortBy()).containsExactly("sort1", "sort2");
		assertThat(request.getSortDirection()).hasToString(Direction.ASC.toString());
	}

	@Test
	void hasNoDirtOnCreatedBean() {
		var customerDetailsRequest = new CustomerDetailsRequest();
		assertThat(customerDetailsRequest).hasAllNullFieldsOrPropertiesExcept("page", "limit", "sortDirection");
	}
}
