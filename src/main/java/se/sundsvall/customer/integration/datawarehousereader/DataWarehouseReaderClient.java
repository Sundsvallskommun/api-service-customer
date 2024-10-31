package se.sundsvall.customer.integration.datawarehousereader;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static se.sundsvall.customer.integration.datawarehousereader.configuration.DataWarehouseReaderConfiguration.CLIENT_ID;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import generated.se.sundsvall.datawarehousereader.CustomerDetailsResponse;
import generated.se.sundsvall.datawarehousereader.CustomerEngagementResponse;
import generated.se.sundsvall.datawarehousereader.Direction;
import se.sundsvall.customer.integration.datawarehousereader.configuration.DataWarehouseReaderConfiguration;

@FeignClient(name = CLIENT_ID, url = "${integration.datawarehousereader.url}", configuration = DataWarehouseReaderConfiguration.class)
public interface DataWarehouseReaderClient {

	@GetMapping(path = "/{municipalityId}/customer/engagements", produces = {
		APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE
	})
	CustomerEngagementResponse getCustomerEngagement(
		@PathVariable("municipalityId") String municipalityId,
		@RequestParam(value = "partyId") String partyId);

	@GetMapping(path = "/{municipalityId}/customer/details", produces = {
		APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE
	})
	CustomerDetailsResponse getCustomerDetails(
		@PathVariable("municipalityId") String municipalityId,
		@RequestParam(value = "partyId") List<String> partyId,
		@RequestParam(value = "customerEngagementOrgId") String customerEngagementOrgId,
		@RequestParam(value = "fromDateTime") String fromDateTime,
		@RequestParam(value = "page") Integer page,
		@RequestParam(value = "limit") Integer limit,
		@RequestParam(value = "sortBy") List<String> sortBy,
		@RequestParam(value = "sortDirection") Direction sortDirection);
}
