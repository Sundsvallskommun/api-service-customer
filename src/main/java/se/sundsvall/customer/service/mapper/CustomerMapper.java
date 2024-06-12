package se.sundsvall.customer.service.mapper;

import static com.nimbusds.oauth2.sdk.util.CollectionUtils.isEmpty;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.zalando.problem.Status.NOT_FOUND;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Sort;
import org.zalando.problem.Problem;

import generated.se.sundsvall.datawarehousereader.CustomerEngagement;
import generated.se.sundsvall.datawarehousereader.CustomerEngagementResponse;
import se.sundsvall.customer.api.model.Customer;
import se.sundsvall.customer.api.model.CustomerDetails;
import se.sundsvall.customer.api.model.CustomerDetailsResponse;
import se.sundsvall.customer.api.model.CustomerRelation;
import se.sundsvall.customer.api.model.CustomerType;
import se.sundsvall.dept44.models.api.paging.PagingAndSortingMetaData;

public class CustomerMapper {

	private static final String ERROR_NO_CUSTOMER_FOUND = "No customer matched search criteria!";

	private static final String ERROR_NO_CUSTOMER_TYPE_FOUND = "No valid customerType was found!";

	private static final String ERROR_DATAWAREHOUSEREADER_CUSTOMERTYPE_WAS_NULL = "DataWarehouseReader enum customerType was null!";

	private CustomerMapper() {}

	public static Customer toCustomer(CustomerEngagementResponse dwrResponse) {
		return Customer.create()
			.withCustomerRelations(toCustomerRelations(dwrResponse))
			.withCustomerType(toCustomerType(dwrResponse));
	}

	public static CustomerType toCustomerType(generated.se.sundsvall.datawarehousereader.CustomerType dwrCustomerType) {
		if (isNull(dwrCustomerType)) {
			throw new IllegalArgumentException(ERROR_DATAWAREHOUSEREADER_CUSTOMERTYPE_WAS_NULL);
		}
		return switch (dwrCustomerType) {
			case ENTERPRISE -> CustomerType.ENTERPRISE;
			case PRIVATE -> CustomerType.PRIVATE;
		};
	}

	private static List<CustomerRelation> toCustomerRelations(CustomerEngagementResponse dwrResponse) {
		final var customerRelationList = ofNullable(dwrResponse.getCustomerEngagements()).orElse(emptyList()).stream()
			.map(ce -> CustomerRelation.create()
				.withCustomerNumber(ce.getCustomerNumber())
				.withOrganizationName(ce.getOrganizationName())
				.withOrganizationNumber(ce.getOrganizationNumber())
				.withActive(ofNullable(ce.getActive()).orElse(false))
				.withMoveInDate(ce.getMoveInDate()))
			.toList();

		if (isEmpty(customerRelationList)) {
			throw Problem.valueOf(NOT_FOUND, ERROR_NO_CUSTOMER_FOUND);
		}

		return customerRelationList;
	}

	private static CustomerType toCustomerType(CustomerEngagementResponse dwrResponse) {
		return ofNullable(dwrResponse.getCustomerEngagements()).orElse(emptyList()).stream()
			.map(CustomerEngagement::getCustomerType)
			.filter(Objects::nonNull)
			.map(CustomerMapper::toCustomerType)
			.findAny()
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, ERROR_NO_CUSTOMER_TYPE_FOUND));
	}

	public static CustomerDetailsResponse toCustomerDetailsResponse(generated.se.sundsvall.datawarehousereader.CustomerDetailsResponse dwrResponse) {
		return ofNullable(dwrResponse)
			.map(r -> CustomerDetailsResponse.create()
				.withCustomerDetails(toCustomerDetails(r.getCustomerDetails()))
				.withMetadata(toMetadata(r.getMeta())))
			.orElse(null);
	}

	private static List<CustomerDetails> toCustomerDetails(List<generated.se.sundsvall.datawarehousereader.CustomerDetails> customerDetails) {
		return ofNullable(customerDetails).orElse(emptyList()).stream()
			.map(CustomerMapper::toCustomerDetail)
			.toList();
	}

	private static CustomerDetails toCustomerDetail(generated.se.sundsvall.datawarehousereader.CustomerDetails dwrDetail) {
		return CustomerDetails.create()
			.withActive(ofNullable(dwrDetail.getActive()).orElse(false))
			.withCareOf(dwrDetail.getCareOf())
			.withCity(dwrDetail.getCity())
			.withCustomerCategoryDescription(dwrDetail.getCustomerCategoryDescription())
			.withCustomerCategoryID(ofNullable(dwrDetail.getCustomerCategoryID()).orElse(0))
			.withCustomerChangedFlg(ofNullable(dwrDetail.getCustomerChangedFlg()).orElse(false))
			.withCustomerEngagementOrgId(dwrDetail.getCustomerEngagementOrgId())
			.withCustomerEngagementOrgName(dwrDetail.getCustomerEngagementOrgName())
			.withCustomerName(dwrDetail.getCustomerName())
			.withCustomerNumber(dwrDetail.getCustomerNumber())
			.withEmails(dwrDetail.getEmails())
			.withInstalledChangedFlg(ofNullable(dwrDetail.getInstalledChangedFlg()).orElse(false))
			.withMoveInDate(ofNullable(dwrDetail.getMoveInDate()).orElse(null))
			.withPartyId(dwrDetail.getPartyId())
			.withPhoneNumbers(dwrDetail.getPhoneNumbers())
			.withPostalCode(dwrDetail.getPostalCode())
			.withStreet(dwrDetail.getStreet());
	}

	private static PagingAndSortingMetaData toMetadata(generated.se.sundsvall.datawarehousereader.PagingAndSortingMetaData meta) {
		return ofNullable(meta)
			.map(m -> PagingAndSortingMetaData.create()
				.withCount(ofNullable(m.getCount()).orElse(0))
				.withLimit(ofNullable(m.getLimit()).orElse(0))
				.withPage(ofNullable(m.getPage()).orElse(0))
				.withSortBy(ofNullable(m.getSortBy()).orElse(null))
				.withSortDirection(toSortDirection(m.getSortDirection()))
				.withTotalPages(ofNullable(m.getTotalPages()).orElse(0))
				.withTotalRecords(ofNullable(m.getTotalRecords()).orElse(0L)))
			.orElse(null);
	}

	private static Sort.Direction toSortDirection(generated.se.sundsvall.datawarehousereader.Direction direction) {
		return ofNullable(direction)
			.map(d -> Sort.Direction.fromString(d.getValue()))
			.orElse(null);
	}
}
