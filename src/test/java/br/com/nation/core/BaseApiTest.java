package br.com.nation.core;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

public class BaseApiTest {

    @BeforeAll
    public static void setupGlobal() {
        RestAssured.baseURI = "https://serverest.dev";

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }
}