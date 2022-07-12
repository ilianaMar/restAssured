package api.tests.utils;

import static io.restassured.RestAssured.*;

import api.tests.config.AuthHelper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

public class PaypalTestBase {
    public static String accessToken;
    private static final String path = "src/test/java/api/config/paypal-auth.json";
    private static final String baseUri = "https://api.sandbox.paypal.com/";
    private static final String basePath = "v1";
    public static RequestSpecification reqSpec;
    public static ResponseSpecification respSpec;

    protected static void init() {
        reqSpec = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .addHeader("Accept", "*/*")
                .build();
        respSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
        RestAssured.responseSpecification = respSpec;
        RestAssured.requestSpecification = reqSpec;
    }

    public static void generateToken() {
        init();
        Map<?, ?> paypalAuthData = new AuthHelper(path).getJson();
        Object paypalClientId = paypalAuthData.get("client_id");
        Object paypalClientSecret = paypalAuthData.get("client_secret");

        accessToken = given()
                .params("grant_type", "client_credentials")
                .auth()
                .preemptive()
                .basic(paypalClientId.toString(), paypalClientSecret.toString())
                .when()
                .post("/oauth2/token")
                .then()
                .extract()
                .path("access_token");
        System.out.println("The token is: " + accessToken);
    }
}
