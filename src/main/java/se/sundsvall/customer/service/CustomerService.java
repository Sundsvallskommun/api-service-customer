package se.sundsvall.customer.service;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Optional.ofNullable;
import static org.springframework.util.CollectionUtils.isEmpty;
import static se.sundsvall.customer.service.mapper.CustomerMapper.toCustomer;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import se.sundsvall.customer.api.model.Customer;
import se.sundsvall.customer.api.model.CustomerDetailsRequest;
import se.sundsvall.customer.integration.datawarehousereader.DataWarehouseReaderClient;

import generated.se.sundsvall.datawarehousereader.CustomerDetailsResponse;

@Service
public class CustomerService {

	static final DateTimeFormatter DATE_TIME_FORMAT = ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

	private final DataWarehouseReaderClient dataWarehouseReaderClient;

	CustomerService(final DataWarehouseReaderClient dataWarehouseReaderClient) {
		this.dataWarehouseReaderClient = dataWarehouseReaderClient;
	}

	public Customer getCustomer(String partyId) {
		return toCustomer(dataWarehouseReaderClient.getCustomerEngagement(partyId));
	}

	public CustomerDetailsResponse getCustomerDetails(final CustomerDetailsRequest request) {
		if (!isEmpty(request.getPartyId())) {
			return dataWarehouseReaderClient.getCustomerDetailsByPartyId(
				request.getPartyId(), fromOffsetDateTimeToString(request.getFromDateTime()));
		} else {
			return dataWarehouseReaderClient.getCustomerDetailsByCustomerEngagementOrgId(
				request.getCustomerEngagementOrgId(), fromOffsetDateTimeToString(request.getFromDateTime()));
		}
	}

	private String fromOffsetDateTimeToString(OffsetDateTime fromDateTime) {
		return ofNullable(fromDateTime)
			.map(DATE_TIME_FORMAT::format)
			.orElse(null);
	}
}
