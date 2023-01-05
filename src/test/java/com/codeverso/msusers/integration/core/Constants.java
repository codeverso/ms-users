package com.codeverso.msusers.integration.core;

import io.restassured.http.ContentType;

public interface Constants {

    String API_BASE_URI = "http://localhost";
    Integer API_PORT = 8080;
    String API_BASE_PATH = "";

    ContentType APP_CONTENT_TYPE = ContentType.JSON;
    Long MAX_TIMEOUT = 5000L;
}
