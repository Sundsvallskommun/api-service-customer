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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import com.google.code.beanmatchers.BeanMatchers;

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
		final var sortBy = List.of("sort1", "sort2");
		final var page = 1;
		final var limit = 10;

		final var request = new CustomerDetailsRequest()
			.withPartyId(List.of(partyId))
			.withCustomerEngagementOrgId(customerEngagementOrgId)
			.withFromDateTime(fromDateTime);
		request.setSortBy(sortBy);
		request.setPage(page);
		request.setLimit(limit);

		assertThat(request).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(request.getPartyId()).containsExactly(partyId);
		assertThat(request.getCustomerEngagementOrgId()).isEqualTo(customerEngagementOrgId);
		assertThat(request.getFromDateTime()).isEqualTo(fromDateTime);
		assertThat(request.getPage()).isEqualTo(page);
		assertThat(request.getLimit()).isEqualTo(limit);
		assertThat(request.getSortBy()).isEqualTo(sortBy);
		assertThat(request.getSortDirection()).isEqualTo(Sort.DEFAULT_DIRECTION);
	}

	@Test
	void hasNoDirtOnCreatedBean() {
		final var customerDetailsRequest = new CustomerDetailsRequest();
		assertThat(customerDetailsRequest).hasAllNullFieldsOrPropertiesExcept("page", "limit", "sortDirection")
			.hasFieldOrPropertyWithValue("page", 1)
			.hasFieldOrPropertyWithValue("limit", 100)
			.hasFieldOrPropertyWithValue("sortDirection", Sort.DEFAULT_DIRECTION);
	}
}
