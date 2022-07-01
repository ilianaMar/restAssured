package api.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class TestBase {
    public static RequestSpecification reqSpec;
    public static ResponseSpecification respSpec;

    public static void init(String baseUri, String basePath) {
        reqSpec = new RequestSpecBuilder().setBaseUri(baseUri).setBasePath(basePath).build();
        respSpec = new ResponseSpecBuilder().build();
        RestAssured.responseSpecification = respSpec;
        RestAssured.requestSpecification = reqSpec;
    }
}
