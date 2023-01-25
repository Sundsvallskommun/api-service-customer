package se.sundsvall.customer.api.model;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Customer relation model")
public class CustomerRelation {

	@Schema(description = "Customer number", example = "10007", accessMode = READ_ONLY)
	private String customerNumber;

	@Schema(description = "Organization number", example = "5565027223", accessMode = READ_ONLY)
	private String organizationNumber;

	@Schema(description = "Organization name", example = "Sundsvall Elnät", accessMode = READ_ONLY)
	private String organizationName;

	public static CustomerRelation create() {
		return new CustomerRelation();
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public CustomerRelation withCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
		return this;
	}

	public String getOrganizationNumber() {
		return organizationNumber;
	}

	public void setOrganizationNumber(String organizationNumber) {
		this.organizationNumber = organizationNumber;
	}

	public CustomerRelation withOrganizationNumber(String organizationNumber) {
		this.organizationNumber = organizationNumber;
		return this;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public CustomerRelation withOrganizationName(String organizationName) {
		this.organizationName = organizationName;
		return this;
	}

	@Override
	public int hashCode() { return Objects.hash(customerNumber, organizationName, organizationNumber); }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerRelation other = (CustomerRelation) obj;
		return Objects.equals(customerNumber, other.customerNumber) && Objects.equals(organizationName, other.organizationName) && Objects.equals(organizationNumber,
			other.organizationNumber);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerRelation [customerNumber=").append(customerNumber).append(", organizationNumber=").append(organizationNumber).append(", organizationName=").append(
			organizationName).append("]");
		return builder.toString();
	}
}
