package se.sundsvall.customer.api.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import se.sundsvall.dept44.common.validators.annotation.ValidOrganizationNumber;
import se.sundsvall.dept44.common.validators.annotation.ValidUuid;
import se.sundsvall.dept44.models.api.paging.AbstractParameterPagingAndSortingBase;

@Schema(description = "Customer details request")
public class CustomerDetailsRequest extends AbstractParameterPagingAndSortingBase {

	@ArraySchema(schema = @Schema(description = "List of Party-IDs"))
	private List<@ValidUuid String> partyId;

	@ValidOrganizationNumber
	@Schema(description = "Organization id for customer engagements", requiredMode = REQUIRED)
	private String customerEngagementOrgId;

	@Schema(description = "Earliest date and time for when to search for change from. Format is yyyy-MM-dd'T'HH:mm:ss.SSSXXX", example = "2000-10-31T01:30:00.000-05:00")
	private OffsetDateTime fromDateTime;

	public List<String> getPartyId() {
		return partyId;
	}

	public void setPartyId(final List<String> partyId) {
		this.partyId = partyId;
	}

	public CustomerDetailsRequest withPartyId(final List<String> partyId) {
		this.partyId = partyId;
		return this;
	}

	public String getCustomerEngagementOrgId() {
		return customerEngagementOrgId;
	}

	public void setCustomerEngagementOrgId(final String customerEngagementOrgId) {
		this.customerEngagementOrgId = customerEngagementOrgId;
	}

	public CustomerDetailsRequest withCustomerEngagementOrgId(final String customerEngagementOrgId) {
		this.customerEngagementOrgId = customerEngagementOrgId;
		return this;
	}

	public OffsetDateTime getFromDateTime() {
		return fromDateTime;
	}

	public void setFromDateTime(final OffsetDateTime fromDateTime) {
		this.fromDateTime = fromDateTime;
	}

	public CustomerDetailsRequest withFromDateTime(final OffsetDateTime fromDateTime) {
		this.fromDateTime = fromDateTime;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass() || !super.equals(o)) {
			return false;
		}
		final var that = (CustomerDetailsRequest) o;
		return Objects.equals(partyId, that.partyId) && Objects.equals(customerEngagementOrgId, that.customerEngagementOrgId) && Objects.equals(fromDateTime, that.fromDateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), partyId, customerEngagementOrgId, fromDateTime);
	}
}
