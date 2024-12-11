package se.sundsvall.customer.service;

import static java.time.format.DateTimeFormatter.ofPattern;
import static se.sundsvall.customer.service.mapper.CustomerMapper.toCustomer;
import static se.sundsvall.customer.service.mapper.CustomerMapper.toCustomerDetailsResponse;

import generated.se.sundsvall.datawarehousereader.Direction;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.stereotype.Service;
import se.sundsvall.customer.api.model.Customer;
import se.sundsvall.customer.api.model.CustomerDetailsRequest;
import se.sundsvall.customer.api.model.CustomerDetailsResponse;
import se.sundsvall.customer.integration.datawarehousereader.DataWarehouseReaderClient;

@Service
public class CustomerService {

	static final DateTimeFormatter DATE_TIME_FORMAT = ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

	private final DataWarehouseReaderClient dataWarehouseReaderClient;

	CustomerService(final DataWarehouseReaderClient dataWarehouseReaderClient) {
		this.dataWarehouseReaderClient = dataWarehouseReaderClient;
	}

	public Customer getCustomer(String municipalityId, String partyId) {
		return toCustomer(dataWarehouseReaderClient.getCustomerEngagement(municipalityId, partyId));
	}

	public CustomerDetailsResponse getCustomerDetails(String municipalityId, CustomerDetailsRequest request) {
		return toCustomerDetailsResponse(dataWarehouseReaderClient.getCustomerDetails(
			municipalityId,
			request.getPartyId(),
			request.getCustomerEngagementOrgId(),
			// If no date is provided, send nothing
			Optional.ofNullable(request.getFromDateTime()).map(fromDate -> fromDate.format(DATE_TIME_FORMAT)).orElse(null),
			request.getPage(),
			request.getLimit(),
			request.getSortBy(),
			Direction.fromValue(request.getSortDirection().toString())));
	}
}
