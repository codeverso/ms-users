package com.codeverso.msusers.integration;

import com.codeverso.msusers.integration.core.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Users Endpoint Integration Tests")
public class UserControllerIT extends BaseTest {

    @Test
    @DisplayName("Should return all users")
    public void testShouldReturnAllUsers() {

        String USER_ID = "ec45afff-ca6a-4701-accb-4fc85b1b31dd";

        given()
            .when()
                .get("/users")
            .then()
                .statusCode(200)
                .body("find{it.uuid == '"+ USER_ID +"'}.name", is("Murillo"))
        ;
    }
}


