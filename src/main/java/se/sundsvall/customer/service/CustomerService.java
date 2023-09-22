package se.sundsvall.customer.service;

import generated.se.sundsvall.datawarehousereader.CustomerDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.sundsvall.customer.api.model.Customer;
import se.sundsvall.customer.integration.datawarehousereader.DataWarehouseReaderClient;

import java.time.OffsetDateTime;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Optional.ofNullable;
import static se.sundsvall.customer.service.mapper.CustomerMapper.toCustomer;

@Service
public class CustomerService {

	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	@Autowired
	private DataWarehouseReaderClient dataWarehouseReaderClient;

	public Customer getCustomer(String partyId) {
		return toCustomer(dataWarehouseReaderClient.getCustomerEngagement(partyId));
	}

	public CustomerDetailsResponse getCustomerDetails(List<String> partyId, OffsetDateTime fromDateTime) {
		return dataWarehouseReaderClient.getCustomerDetails(partyId, fromOffsetDateTimeToString(fromDateTime));
	}

	private String fromOffsetDateTimeToString(OffsetDateTime fromDateTime) {
		return ofNullable(fromDateTime)
			.map(offsetDateTime -> offsetDateTime.format(ofPattern(DATE_TIME_FORMAT)))
			.orElse(null);
	}
}
