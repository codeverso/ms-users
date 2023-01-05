package com.codeverso.msusers.integration.core;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;


public abstract class BaseTest implements Constants {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = API_BASE_URI;
        RestAssured.port = API_PORT;
        RestAssured.basePath = API_BASE_PATH;

        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setContentType(APP_CONTENT_TYPE);
        RestAssured.requestSpecification = reqBuilder.build();

        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectResponseTime(Matchers.lessThan(MAX_TIMEOUT));
        RestAssured.responseSpecification = resBuilder.build();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

}
