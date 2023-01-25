package se.sundsvall.customer.service;

import static se.sundsvall.customer.service.mapper.CustomerMapper.toCustomer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.sundsvall.customer.api.model.Customer;
import se.sundsvall.customer.integration.datawarehousereader.DataWarehouseReaderClient;

@Service
public class CustomerService {

	@Autowired
	private DataWarehouseReaderClient dataWarehouseReaderClient;

	public Customer getCustomer(String partyId) {
		return toCustomer(dataWarehouseReaderClient.getCustomerEngagement(partyId));
	}
}
