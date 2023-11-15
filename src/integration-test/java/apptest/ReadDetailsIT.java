package apptest;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.Test;

import se.sundsvall.customer.Application;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;

import generated.se.sundsvall.datawarehousereader.CustomerDetailsResponse;

@WireMockAppTestSuite(files = "classpath:/ReadDetails/", classes = Application.class)
class ReadDetailsIT extends AbstractAppTest {

	@Test
	void test1_readDetailsByPartyIdAndEngagementOrgId() throws Exception {
		setupCall()
				.withServicePath("/details")
				.withHttpMethod(POST)
				.withRequest("request.json")
				.withExpectedResponse("response.json")
				.withExpectedResponseStatus(OK)
				.sendRequestAndVerifyResponse()
				.andReturnBody(CustomerDetailsResponse.class);
	}

	@Test
	void test2_readDetailsByCustomerEngagementOrgIdWithFromDate() throws Exception {
		setupCall()
				.withServicePath("/details")
				.withHttpMethod(POST)
				.withRequest("request.json")
				.withExpectedResponse("response.json")
				.withExpectedResponseStatus(OK)
				.sendRequestAndVerifyResponse()
				.andReturnBody(CustomerDetailsResponse.class);
	}

	@Test
	void test3_readDetailsByCustomerEngagementOrgId() throws Exception {
		setupCall()
			.withServicePath("/details")
			.withHttpMethod(POST)
			.withRequest("request.json")
			.withExpectedResponse("response.json")
			.withExpectedResponseStatus(OK)
			.sendRequestAndVerifyResponse()
			.andReturnBody(CustomerDetailsResponse.class);
	}

	@Test
	void test4_readDetailsByCustomerEngagementOrgIdNotFound() throws Exception {
		setupCall()
			.withServicePath("/details")
			.withHttpMethod(POST)
			.withRequest("request.json")
			.withExpectedResponse("response.json")
			.withExpectedResponseStatus(OK)
			.sendRequestAndVerifyResponse()
			.andReturnBody(CustomerDetailsResponse.class);
	}
}
