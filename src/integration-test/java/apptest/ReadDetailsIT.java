package apptest;

import static org.assertj.core.api.Assertions.assertThat;
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
		final var result = setupCall()
				.withServicePath("/details")
				.withHttpMethod(POST)
				.withRequest("request.json")
				.withExpectedResponse("response.json")
				.withExpectedResponseStatus(OK)
				.sendRequestAndVerifyResponse()
				.andReturnBody(CustomerDetailsResponse.class);

		assertThat(result.getCustomerDetails()).isNotEmpty();
		assertThat(result.getCustomerDetails()).hasSize(1);
		assertThat(result.getMeta().getCount()).isEqualTo(1);
		assertThat(result.getMeta().getTotalRecords()).isEqualTo(1);
		assertThat(result.getMeta().getTotalPages()).isEqualTo(1);
	}

	@Test
	void test2_readDetailsByPartyIdNotFound() throws Exception {
		final var result = setupCall()
			.withServicePath("/details")
			.withHttpMethod(POST)
			.withRequest("request.json")
			.withExpectedResponse("response.json")
			.withExpectedResponseStatus(OK)
			.sendRequestAndVerifyResponse()
			.andReturnBody(CustomerDetailsResponse.class);

		assertThat(result.getCustomerDetails()).isEmpty();
		assertThat(result.getMeta().getCount()).isZero();
		assertThat(result.getMeta().getTotalRecords()).isZero();
		assertThat(result.getMeta().getTotalPages()).isZero();
	}

	@Test
	void test3_readDetailsByCustomerEngagementOrgId() throws Exception {
		final var result = setupCall()
			.withServicePath("/details")
			.withHttpMethod(POST)
			.withRequest("request.json")
			.withExpectedResponse("response.json")
			.withExpectedResponseStatus(OK)
			.sendRequestAndVerifyResponse()
			.andReturnBody(CustomerDetailsResponse.class);

		assertThat(result.getCustomerDetails()).isNotEmpty();
		assertThat(result.getCustomerDetails()).hasSize(1);
		assertThat(result.getMeta().getCount()).isEqualTo(1);
		assertThat(result.getMeta().getTotalRecords()).isEqualTo(1);
		assertThat(result.getMeta().getTotalPages()).isEqualTo(1);
	}

	@Test
	void test4_readDetailsByCustomerEngagementOrgIdNotFound() throws Exception {
		final var result = setupCall()
			.withServicePath("/details")
			.withHttpMethod(POST)
			.withRequest("request.json")
			.withExpectedResponse("response.json")
			.withExpectedResponseStatus(OK)
			.sendRequestAndVerifyResponse()
			.andReturnBody(CustomerDetailsResponse.class);

		assertThat(result.getCustomerDetails()).isEmpty();
		assertThat(result.getMeta().getCount()).isZero();
		assertThat(result.getMeta().getTotalRecords()).isZero();
		assertThat(result.getMeta().getTotalPages()).isZero();
	}

	@Test
	void test5_readDetailsByCustomerEngagementOrgIdWithFromDate() throws Exception {
		final var result = setupCall()
			.withServicePath("/details")
			.withHttpMethod(POST)
			.withRequest("request.json")
			.withExpectedResponse("response.json")
			.withExpectedResponseStatus(OK)
			.sendRequestAndVerifyResponse()
			.andReturnBody(CustomerDetailsResponse.class);

		assertThat(result.getCustomerDetails()).isNotEmpty();
		assertThat(result.getCustomerDetails()).hasSize(1);
		assertThat(result.getMeta().getCount()).isEqualTo(1);
		assertThat(result.getMeta().getTotalRecords()).isEqualTo(1);
		assertThat(result.getMeta().getTotalPages()).isEqualTo(1);
	}
}
