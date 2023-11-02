package se.sundsvall.customer.service;

import generated.se.sundsvall.datawarehousereader.CustomerDetailsResponse;
import org.springframework.stereotype.Service;
import se.sundsvall.customer.api.model.Customer;
import se.sundsvall.customer.api.model.CustomerDetailsRequest;
import se.sundsvall.customer.integration.datawarehousereader.DataWarehouseReaderClient;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Optional.ofNullable;
import static se.sundsvall.customer.service.mapper.CustomerMapper.toCustomer;

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
		return dataWarehouseReaderClient.getCustomerDetails(
			request.getPartyId(),
			request.getCustomerEngagementOrgId(),
			fromOffsetDateTimeToString(request.getFromDateTime()));
	}

	private String fromOffsetDateTimeToString(OffsetDateTime fromDateTime) {
		return ofNullable(fromDateTime)
			.map(DATE_TIME_FORMAT::format)
			.orElse(null);
	}
}
