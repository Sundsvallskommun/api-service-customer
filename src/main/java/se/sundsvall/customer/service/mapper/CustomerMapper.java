package se.sundsvall.customer.service.mapper;

import static com.nimbusds.oauth2.sdk.util.CollectionUtils.isEmpty;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static org.zalando.problem.Status.NOT_FOUND;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.zalando.problem.Problem;

import generated.se.sundsvall.datawarehousereader.CustomerEngagement;
import generated.se.sundsvall.datawarehousereader.CustomerEngagementResponse;
import se.sundsvall.customer.api.model.Customer;
import se.sundsvall.customer.api.model.CustomerRelation;
import se.sundsvall.customer.api.model.CustomerType;

public class CustomerMapper {

	private static final String ERROR_NO_CUSTOMER_FOUND = "No customer matched search criteria!";
	private static final String ERROR_NO_CUSTOMER_TYPE_FOUND = "No valid customerType was found!";
	private static final String ERROR_DATAWAREHOUSEREADER_CUSTOMERTYPE_WAS_NULL = "DataWarehouseReader enum customerType was null!";

	private CustomerMapper() {}

	public static Customer toCustomer(CustomerEngagementResponse customerEngagementResponse) {
		return Customer.create()
			.withCustomerRelations(toCustomerRelations(customerEngagementResponse))
			.withCustomerType(toCustomerType(customerEngagementResponse));
	}

	public static CustomerType toCustomerType(generated.se.sundsvall.datawarehousereader.CustomerType dataWarehouseReaderCustomerType) {
		if (isNull(dataWarehouseReaderCustomerType)) {
			throw new IllegalArgumentException(ERROR_DATAWAREHOUSEREADER_CUSTOMERTYPE_WAS_NULL);
		}
		return switch (dataWarehouseReaderCustomerType) {
			case ENTERPRISE -> CustomerType.ENTERPRISE;
			case PRIVATE -> CustomerType.PRIVATE;
		};
	}

	private static List<CustomerRelation> toCustomerRelations(CustomerEngagementResponse customerEngagementResponse) {
		final var customerRelationList = Optional.ofNullable(customerEngagementResponse.getCustomerEngagements()).orElse(emptyList()).stream()
			.map(ce -> CustomerRelation.create()
				.withCustomerNumber(ce.getCustomerNumber())
				.withOrganizationName(ce.getOrganizationName())
				.withOrganizationNumber(ce.getOrganizationNumber()))
			.toList();

		if (isEmpty(customerRelationList)) {
			throw Problem.valueOf(NOT_FOUND, ERROR_NO_CUSTOMER_FOUND);
		}

		return customerRelationList;
	}

	private static CustomerType toCustomerType(CustomerEngagementResponse customerEngagementResponse) {
		return Optional.ofNullable(customerEngagementResponse.getCustomerEngagements()).orElse(emptyList()).stream()
			.map(CustomerEngagement::getCustomerType)
			.filter(Objects::nonNull)
			.map(CustomerMapper::toCustomerType)
			.findAny()
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, ERROR_NO_CUSTOMER_TYPE_FOUND));
	}
}
