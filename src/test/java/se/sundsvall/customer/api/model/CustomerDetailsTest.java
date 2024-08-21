package se.sundsvall.customer.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.code.beanmatchers.BeanMatchers;

class CustomerDetailsTest {

	@BeforeAll
	static void setup() {
		BeanMatchers.registerValueGenerator(() -> LocalDate.now().plusDays(new Random().nextInt()), LocalDate.class);
	}

	@Test
	void isValidBean() {
		assertThat(CustomerDetails.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals()));
	}

	@Test
	void hasValidBuilderMethods() {

		final var active = true;
		final var careOf = "careOf";
		final var city = "city";
		final var customerCategoryDescription = "customerCategoryDescription";
		final var customerCategoryID = 123;
		final var customerChangedFlg = true;
		final var customerEngagementOrgId = "customerEngagementOrgId";
		final var customerEngagementOrgName = "customerEngagementOrgName";
		final var customerName = "customerName";
		final var customerNumber = "customerNumber";
		final var emails = List.of("email1", "email2");
		final var installedChangedFlg = true;
		final var moveInDate = LocalDate.now();
		final var partyId = "partyId";
		final var phoneNumbers = List.of("phoneNumber1", "phoneNumber2");
		final var postalCode = "postalCode";
		final var street = "street";

		final var bean = CustomerDetails.create()
			.withActive(active)
			.withCareOf(careOf)
			.withCity(city)
			.withCustomerCategoryDescription(customerCategoryDescription)
			.withCustomerCategoryID(customerCategoryID)
			.withCustomerChangedFlg(customerChangedFlg)
			.withCustomerEngagementOrgId(customerEngagementOrgId)
			.withCustomerEngagementOrgName(customerEngagementOrgName)
			.withCustomerName(customerName)
			.withCustomerNumber(customerNumber)
			.withEmails(emails)
			.withInstalledChangedFlg(installedChangedFlg)
			.withMoveInDate(moveInDate)
			.withPartyId(partyId)
			.withPhoneNumbers(phoneNumbers)
			.withPostalCode(postalCode)
			.withStreet(street);

		assertThat(bean).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(bean.getCareOf()).isEqualTo(careOf);
		assertThat(bean.getCity()).isEqualTo(city);
		assertThat(bean.getCustomerCategoryDescription()).isEqualTo(customerCategoryDescription);
		assertThat(bean.getCustomerCategoryID()).isEqualTo(customerCategoryID);
		assertThat(bean.getCustomerEngagementOrgId()).isEqualTo(customerEngagementOrgId);
		assertThat(bean.getCustomerEngagementOrgName()).isEqualTo(customerEngagementOrgName);
		assertThat(bean.getCustomerName()).isEqualTo(customerName);
		assertThat(bean.getCustomerNumber()).isEqualTo(customerNumber);
		assertThat(bean.getEmails()).isEqualTo(emails);
		assertThat(bean.getMoveInDate()).isEqualTo(moveInDate);
		assertThat(bean.getPartyId()).isEqualTo(partyId);
		assertThat(bean.getPhoneNumbers()).isEqualTo(phoneNumbers);
		assertThat(bean.getPostalCode()).isEqualTo(postalCode);
		assertThat(bean.getStreet()).isEqualTo(street);
		assertThat(bean.isActive()).isEqualTo(active);
		assertThat(bean.isCustomerChangedFlg()).isEqualTo(customerChangedFlg);
		assertThat(bean.isInstalledChangedFlg()).isEqualTo(installedChangedFlg);
	}

	@Test
	void hasNoDirtOnCreatedBean() {
		assertThat(new CustomerDetails()).hasAllNullFieldsOrPropertiesExcept("active", "customerCategoryID", "customerChangedFlg", "installedChangedFlg")
			.hasFieldOrPropertyWithValue("active", false)
			.hasFieldOrPropertyWithValue("customerCategoryID", 0)
			.hasFieldOrPropertyWithValue("customerChangedFlg", false)
			.hasFieldOrPropertyWithValue("installedChangedFlg", false);

		assertThat(CustomerDetails.create()).hasAllNullFieldsOrPropertiesExcept("active", "customerCategoryID", "customerChangedFlg", "installedChangedFlg")
			.hasFieldOrPropertyWithValue("active", false)
			.hasFieldOrPropertyWithValue("customerCategoryID", 0)
			.hasFieldOrPropertyWithValue("customerChangedFlg", false)
			.hasFieldOrPropertyWithValue("installedChangedFlg", false);
	}
}
