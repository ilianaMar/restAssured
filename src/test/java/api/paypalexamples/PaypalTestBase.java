package api.paypalexamples;

import static io.restassured.RestAssured.*;

import api.config.AuthHelper;
import io.restassured.RestAssured;

import java.util.Map;

public class PaypalTestBase {
    public static String accessToken;
    private static final String path = "src/test/java/api/config/paypal-auth.json";

    public static void init() {
        Map<?, ?> paypalAuthData = new AuthHelper(path).getJson();
        Object paypalClientId = paypalAuthData.get("client_id");
        Object paypalClientSecret = paypalAuthData.get("client_secret");

        RestAssured.baseURI = "https://api.sandbox.paypal.com";
        RestAssured.basePath = "/v1";

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
