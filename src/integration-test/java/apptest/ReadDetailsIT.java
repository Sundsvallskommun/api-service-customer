package apptest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.Test;

import se.sundsvall.customer.Application;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;

import generated.se.sundsvall.datawarehousereader.CustomerDetailsResponse;

@WireMockAppTestSuite(files = "classpath:/ReadDetails/", classes = Application.class)
public class ReadDetailsIT extends AbstractAppTest {

	@Test
	void test1_readDetails() throws Exception {

		final var result =	setupCall()
			.withServicePath("/details/3b7a5955-f481-42bd-a2b3-6ef8bd76b105")
			.withHttpMethod(GET)
			.withExpectedResponse("response.json")
			.withExpectedResponseStatus(OK)
			.sendRequestAndVerifyResponse()
			.andReturnBody(CustomerDetailsResponse.class);

		assertThat(result.getCustomerDetails()).isNotEmpty();
		assertThat(result.getCustomerDetails().size()).isEqualTo(1);
		assertThat(result.getMeta().getCount()).isEqualTo(1);
		assertThat(result.getMeta().getTotalRecords()).isEqualTo(1);
		assertThat(result.getMeta().getTotalPages()).isEqualTo(1);
	}

	@Test
	void test2_readDetailsNotFound() throws Exception {

	final var result =	setupCall()
			.withServicePath("/details/1d2a3bb3-3a38-4d65-9910-79dd9efa06c8")
			.withHttpMethod(GET)
			.withExpectedResponse("response.json")
			.withExpectedResponseStatus(OK)
			.sendRequestAndVerifyResponse()
			.andReturnBody(CustomerDetailsResponse.class);

	assertThat(result.getCustomerDetails()).isEmpty();
	assertThat(result.getMeta().getCount()).isEqualTo(0);
	assertThat(result.getMeta().getTotalRecords()).isEqualTo(0);
	assertThat(result.getMeta().getTotalPages()).isEqualTo(0);
	}
}
