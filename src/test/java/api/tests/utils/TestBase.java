package api.tests.utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class TestBase {
    public  RequestSpecification reqSpec;
    public  ResponseSpecification respSpec;

    public void buildReqSpec(String baseUri, String basePath){
        reqSpec = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .addHeader("Accept", "*/*")
                .build();
        RestAssured.requestSpecification = reqSpec;
    }

    public ResponseSpecification buildRespSpec() {
        respSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();

        RestAssured.responseSpecification = respSpec;
        return respSpec;
    }
}
