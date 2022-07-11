package api.trelloexamples;

import api.config.AuthHelper;
import api.tests.TestBase;

import org.junit.jupiter.api.*;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TrelloApiWithOath1Tests extends TestBase {

    @BeforeAll
    public static void setup() {
        String path = "src/test/java/api/config/trello-auth.json";
        Map<?, ?> authData = new AuthHelper(path).getJson();
        Object apiKey = authData.get("key");
        Object apiToken = authData.get("token");
        Object apiSecret =  authData.get("secret");
        String baseUri = "https://api.trello.com/";
        String basePath = "1/members/";
        init(baseUri, basePath);
        reqSpec.auth()
                .oauth(apiKey.toString(), apiSecret.toString(), apiToken.toString(), apiSecret.toString())
                .log()
                .all();
        respSpec.statusCode(200);
    }

    @Test
    @DisplayName("Get my account info")
    void getUserTrelloInfo(){
        given()
                .get("me")
                .then()
                .log()
                .all()
                .assertThat()
                .body("fullName", containsString("Iliana Markova"));
    }
}
