package se.sundsvall.customer.api.model;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Customer model")
public class Customer {

	@Schema(enumAsRef = true, accessMode = READ_ONLY)
	private CustomerType customerType;

	@ArraySchema(schema = @Schema(implementation = CustomerRelation.class))
	private List<CustomerRelation> customerRelations;

	public static Customer create() {
		return new Customer();
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public Customer withCustomerType(CustomerType customerType) {
		this.customerType = customerType;
		return this;
	}

	public List<CustomerRelation> getCustomerRelations() {
		return customerRelations;
	}

	public void setCustomerRelations(List<CustomerRelation> customerRelations) {
		this.customerRelations = customerRelations;
	}

	public Customer withCustomerRelations(List<CustomerRelation> customerRelations) {
		this.customerRelations = customerRelations;
		return this;
	}

	@Override
	public int hashCode() { return Objects.hash(customerRelations, customerType); }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(customerRelations, other.customerRelations) && customerType == other.customerType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customer [customerType=").append(customerType).append(", customerRelations=").append(customerRelations).append("]");
		return builder.toString();
	}
}
