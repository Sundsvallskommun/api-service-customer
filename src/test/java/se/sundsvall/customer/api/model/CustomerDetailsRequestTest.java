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

		final var bean = new CustomerDetailsRequest()
			.withPartyId(List.of(partyId))
			.withCustomerEngagementOrgId(customerEngagementOrgId)
			.withFromDateTime(fromDateTime);
		bean.setSortBy(sortBy);
		bean.setPage(page);
		bean.setLimit(limit);

		assertThat(bean).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(bean.getPartyId()).containsExactly(partyId);
		assertThat(bean.getCustomerEngagementOrgId()).isEqualTo(customerEngagementOrgId);
		assertThat(bean.getFromDateTime()).isEqualTo(fromDateTime);
		assertThat(bean.getPage()).isEqualTo(page);
		assertThat(bean.getLimit()).isEqualTo(limit);
		assertThat(bean.getSortBy()).isEqualTo(sortBy);
		assertThat(bean.getSortDirection()).isEqualTo(Sort.DEFAULT_DIRECTION);
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
