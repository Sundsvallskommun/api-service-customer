package se.sundsvall.customer.api.model;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Customer details model")
public class CustomerDetails {

	@Schema(description = "Company with which the customer has an engagement (organization number)", example = "5591962591", accessMode = READ_ONLY)
	private String customerEngagementOrgId;

	@Schema(description = "Name of the company the customer has an engagement with", example = "Sundsvall Elnät", accessMode = READ_ONLY)
	private String customerEngagementOrgName;

	@Schema(description = "PartyId (e.g. a personId or an organizationId)", example = "81471222-5798-11e9-ae24-57fa13b361e1", accessMode = READ_ONLY)
	private String partyId;

	@Schema(description = "Customer number", example = "39195", accessMode = READ_ONLY)
	private String customerNumber;

	@Schema(description = "Customer name", example = "Sven Svensson", accessMode = READ_ONLY)
	private String customerName;

	@Schema(description = "Street", example = "Storgatan 44", accessMode = READ_ONLY)
	private String street;

	@Schema(description = "Postal code", example = "85230", accessMode = READ_ONLY)
	private String postalCode;

	@Schema(description = "City", example = "Sundsvall", accessMode = READ_ONLY)
	private String city;

	@Schema(description = "Care of address", example = "Agatha Malm", accessMode = READ_ONLY)
	private String careOf;

	@ArraySchema(schema = @Schema(description = "List of phoneNumbers", example = "076-1234567", accessMode = READ_ONLY))
	private List<String> phoneNumbers;

	@ArraySchema(schema = @Schema(description = "List of emailAddresses", example = "test@test.se", accessMode = READ_ONLY))
	private List<String> emails;

	@Schema(description = "Customer category ID", example = "1", accessMode = READ_ONLY)
	private int customerCategoryID;

	@Schema(description = "Customer category description", example = "Privat", accessMode = READ_ONLY)
	private String customerCategoryDescription;

	@Schema(description = "Indicates if customer details have changed since the search date", example = "PRIVATE", accessMode = READ_ONLY)
	private boolean customerChangedFlg;

	@Schema(description = "Indicates if placement and/or equipment details have changed since the search date", example = "true", accessMode = READ_ONLY)
	private boolean installedChangedFlg;

	@Schema(description = "Indicates customer status, if not active then the moveInDate holds information on when the customer will be activated", example = "true", accessMode = READ_ONLY)
	private boolean active;

	@Schema(description = "The prospective customer's move-in date", accessMode = READ_ONLY)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDate moveInDate;

	public static CustomerDetails create() {
		return new CustomerDetails();
	}

	public CustomerDetails withCustomerNumber(final String customerNumber) {
		this.customerNumber = customerNumber;
		return this;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(final String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public CustomerDetails withCustomerName(final String customerName) {
		this.customerName = customerName;
		return this;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(final String customerName) {
		this.customerName = customerName;
	}

	public CustomerDetails withStreet(final String street) {
		this.street = street;
		return this;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public CustomerDetails withPostalCode(final String postalCode) {
		this.postalCode = postalCode;
		return this;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}

	public CustomerDetails withCity(final String city) {
		this.city = city;
		return this;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public CustomerDetails withCareOf(final String careOf) {
		this.careOf = careOf;
		return this;
	}

	public String getCareOf() {
		return careOf;
	}

	public void setCareOf(final String careOf) {
		this.careOf = careOf;
	}

	public CustomerDetails withPhoneNumbers(final List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
		return this;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(final List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public CustomerDetails withEmails(final List<String> emails) {
		this.emails = emails;
		return this;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(final List<String> emails) {
		this.emails = emails;
	}

	public CustomerDetails withCustomerCategoryID(final int customerCategoryID) {
		this.customerCategoryID = customerCategoryID;
		return this;
	}

	public int getCustomerCategoryID() {
		return customerCategoryID;
	}

	public void setCustomerCategoryID(final int customerCategoryID) {
		this.customerCategoryID = customerCategoryID;
	}

	public CustomerDetails withCustomerCategoryDescription(final String customerCategoryDescription) {
		this.customerCategoryDescription = customerCategoryDescription;
		return this;
	}

	public String getCustomerCategoryDescription() {
		return customerCategoryDescription;
	}

	public void setCustomerCategoryDescription(final String customerCategoryDescription) {
		this.customerCategoryDescription = customerCategoryDescription;
	}

	public CustomerDetails withCustomerChangedFlg(final boolean customerChangedFlg) {
		this.customerChangedFlg = customerChangedFlg;
		return this;
	}

	public boolean isCustomerChangedFlg() {
		return customerChangedFlg;
	}

	public void setCustomerChangedFlg(final boolean customerChangedFlg) {
		this.customerChangedFlg = customerChangedFlg;
	}

	public CustomerDetails withInstalledChangedFlg(final boolean installedChangedFlg) {
		this.installedChangedFlg = installedChangedFlg;
		return this;
	}

	public boolean isInstalledChangedFlg() {
		return installedChangedFlg;
	}

	public void setInstalledChangedFlg(final boolean installedChangedFlg) {
		this.installedChangedFlg = installedChangedFlg;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public CustomerDetails withPartyId(String partyId) {
		this.partyId = partyId;
		return this;
	}

	public CustomerDetails withCustomerEngagementOrgId(String customerEngagementOrgId) {
		this.customerEngagementOrgId = customerEngagementOrgId;
		return this;
	}

	public String getCustomerEngagementOrgId() {
		return customerEngagementOrgId;
	}

	public void setCustomerEngagementOrgId(String customerEngagementOrgId) {
		this.customerEngagementOrgId = customerEngagementOrgId;
	}

	public CustomerDetails withCustomerEngagementOrgName(String customerEngagementName) {
		this.customerEngagementOrgName = customerEngagementName;
		return this;
	}

	public String getCustomerEngagementOrgName() {
		return customerEngagementOrgName;
	}

	public void setCustomerEngagementOrgName(String customerEngagementOrgName) {
		this.customerEngagementOrgName = customerEngagementOrgName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public CustomerDetails withActive(boolean active) {
		this.active = active;
		return this;
	}

	public LocalDate getMoveInDate() {
		return moveInDate;
	}

	public void setMoveInDate(LocalDate moveInDate) {
		this.moveInDate = moveInDate;
	}

	public CustomerDetails withMoveInDate(LocalDate moveInDate) {
		this.moveInDate = moveInDate;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, careOf, city, customerCategoryDescription, customerCategoryID, customerChangedFlg, customerEngagementOrgId, customerEngagementOrgName, customerName, customerNumber, emails, installedChangedFlg,
			moveInDate, partyId, phoneNumbers, postalCode, street);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof final CustomerDetails other)) {
			return false;
		}
		return active == other.active && Objects.equals(careOf, other.careOf) && Objects.equals(city, other.city) && Objects.equals(customerCategoryDescription, other.customerCategoryDescription) && customerCategoryID == other.customerCategoryID
			&& customerChangedFlg == other.customerChangedFlg && Objects.equals(customerEngagementOrgId, other.customerEngagementOrgId) && Objects.equals(customerEngagementOrgName, other.customerEngagementOrgName) && Objects.equals(customerName,
				other.customerName) && Objects.equals(customerNumber, other.customerNumber) && Objects.equals(emails, other.emails) && installedChangedFlg == other.installedChangedFlg
			&& Objects.equals(moveInDate, other.moveInDate) && Objects.equals(partyId, other.partyId) && Objects.equals(phoneNumbers, other.phoneNumbers) && Objects.equals(postalCode, other.postalCode) && Objects.equals(street, other.street);
	}

	@Override
	public String toString() {
		final var builder = new StringBuilder();
		builder.append("CustomerDetails [customerEngagementOrgId=").append(customerEngagementOrgId).append(", customerEngagementOrgName=").append(customerEngagementOrgName).append(", partyId=")
			.append(partyId).append(", customerNumber=").append(customerNumber).append(", customerName=").append(customerName).append(", street=").append(street).append(", postalCode=").append(postalCode).append(", city=").append(city).append(
				", careOf=").append(careOf).append(", phoneNumbers=").append(phoneNumbers).append(", emails=").append(emails).append(", customerCategoryID=").append(customerCategoryID).append(", customerCategoryDescription=").append(
					customerCategoryDescription).append(", customerChangedFlg=").append(customerChangedFlg).append(", installedChangedFlg=").append(installedChangedFlg).append(", active=").append(active).append(", moveInDate=").append(moveInDate)
			.append("]");
		return builder.toString();
	}
}
