package apptest;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.Test;

import se.sundsvall.customer.Application;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;

@WireMockAppTestSuite(files = "classpath:/ReadRelations/", classes = Application.class)
class ReadRelationsIT extends AbstractAppTest {

	@Test
	void test1_readRelation() throws Exception {

		setupCall()
			.withServicePath("/relations/3b7a5955-f481-42bd-a2b3-6ef8bd76b105")
			.withHttpMethod(GET)
			.withExpectedResponse("response.json")
			.withExpectedResponseStatus(OK)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test2_readRelationNotFound() throws Exception {

		setupCall()
			.withServicePath("/relations/1d2a3bb3-3a38-4d65-9910-79dd9efa06c8")
			.withHttpMethod(GET)
			.withExpectedResponse("response.json")
			.withExpectedResponseStatus(NOT_FOUND)
			.sendRequestAndVerifyResponse();
	}
}
