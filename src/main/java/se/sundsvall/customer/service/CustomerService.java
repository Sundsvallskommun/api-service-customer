package se.sundsvall.customer.service;

import static java.time.format.DateTimeFormatter.ofPattern;
import static se.sundsvall.customer.service.mapper.CustomerMapper.toCustomer;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Service;

import generated.se.sundsvall.datawarehousereader.CustomerDetailsResponse;
import generated.se.sundsvall.datawarehousereader.Direction;
import se.sundsvall.customer.api.model.Customer;
import se.sundsvall.customer.api.model.CustomerDetailsRequest;
import se.sundsvall.customer.integration.datawarehousereader.DataWarehouseReaderClient;

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
			// If no date is provided, send nothing
			Optional.ofNullable(request.getFromDateTime()).map(fromDate -> fromDate.format(DATE_TIME_FORMAT)).orElse(null),
			request.getPage(),
			request.getLimit(),
			request.getSortBy(),
			Direction.fromValue(request.getSortDirection().toString()));
	}
}
