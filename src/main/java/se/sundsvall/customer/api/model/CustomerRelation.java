package se.sundsvall.customer.api.model;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "Customer relation model")
public class CustomerRelation {

	@Schema(description = "Customer number", example = "10007", accessMode = READ_ONLY)
	private String customerNumber;

	@Schema(description = "Organization number", example = "5565027223", accessMode = READ_ONLY)
	private String organizationNumber;

	@Schema(description = "Organization name", example = "Sundsvall Elnät", accessMode = READ_ONLY)
	private String organizationName;

	@Schema(description = "Indicates customer status, if not active then the moveInDate holds information on when the customer will be activated", example = "true", accessMode = READ_ONLY)
	private boolean active;

	@Schema(description = "The prospective customer's move-in date", accessMode = READ_ONLY)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDate moveInDate;

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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public CustomerRelation withActive(boolean active) {
		this.active = active;
		return this;
	}

	public LocalDate getMoveInDate() {
		return moveInDate;
	}

	public void setMoveInDate(LocalDate moveInDate) {
		this.moveInDate = moveInDate;
	}

	public CustomerRelation withMoveInDate(LocalDate moveInDate) {
		this.moveInDate = moveInDate;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, customerNumber, moveInDate, organizationName, organizationNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof final CustomerRelation other)) {
			return false;
		}
		return active == other.active && Objects.equals(customerNumber, other.customerNumber) && Objects.equals(moveInDate, other.moveInDate) && Objects.equals(organizationName, other.organizationName) && Objects.equals(organizationNumber,
			other.organizationNumber);
	}

	@Override
	public String toString() {
		final var builder = new StringBuilder();
		builder.append("CustomerRelation [customerNumber=").append(customerNumber).append(", organizationNumber=").append(organizationNumber).append(", organizationName=").append(organizationName).append(", active=").append(active).append(
			", moveInDate=").append(moveInDate).append("]");
		return builder.toString();
	}
}
