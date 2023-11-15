package se.sundsvall.customer.integration.datawarehousereader;

import feign.Param;
import generated.se.sundsvall.datawarehousereader.CustomerDetailsParameters;
import generated.se.sundsvall.datawarehousereader.CustomerDetailsResponse;
import generated.se.sundsvall.datawarehousereader.CustomerEngagementResponse;
import generated.se.sundsvall.datawarehousereader.Direction;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import se.sundsvall.customer.integration.datawarehousereader.configuration.DataWarehouseReaderConfiguration;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static se.sundsvall.customer.integration.datawarehousereader.configuration.DataWarehouseReaderConfiguration.CLIENT_ID;

@FeignClient(name = CLIENT_ID, url = "${integration.datawarehousereader.url}", configuration = DataWarehouseReaderConfiguration.class)
public interface DataWarehouseReaderClient {

	@GetMapping(path = "customer/engagements", produces = {APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
	CustomerEngagementResponse getCustomerEngagement(@RequestParam(value = "partyId") String partyId);

	@GetMapping(path = "customer/details", produces = {APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
	CustomerDetailsResponse getCustomerDetails(
		@RequestParam(value = "partyId") List<String> partyId,
		@RequestParam(value = "customerEngagementOrgId") String customerEngagementOrgId,
		@RequestParam(value = "fromDateTime") String fromDateTime,
		@RequestParam(value = "page") Integer page,
		@RequestParam(value = "limit") Integer limit,
		@RequestParam(value = "sortBy") List<String> sortBy,
		@RequestParam(value = "sortDirection") Direction sortDirection
	);
}
