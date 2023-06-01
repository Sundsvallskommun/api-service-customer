package se.sundsvall.customer.service;

import static se.sundsvall.customer.service.mapper.CustomerMapper.toCustomer;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.sundsvall.customer.api.model.Customer;
import se.sundsvall.customer.integration.datawarehousereader.DataWarehouseReaderClient;

import generated.se.sundsvall.datawarehousereader.CustomerDetailsResponse;

@Service
public class CustomerService {

	@Autowired
	private DataWarehouseReaderClient dataWarehouseReaderClient;

	public Customer getCustomer(String partyId) {
		return toCustomer(dataWarehouseReaderClient.getCustomerEngagement(partyId));
	}

	public CustomerDetailsResponse getCustomerDetails(List<String> partyId, OffsetDateTime fromDateTime) {
		return dataWarehouseReaderClient.getCustomerDetails(partyId, fromDateTime);
	}
}
