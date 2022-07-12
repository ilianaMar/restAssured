package api.tests.loggingexamples;

import api.models.PlaceModel;
import api.tests.utils.TestBase;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class LoggingResponseValuesTests {
    String placeId;
    Faker faker;
    Response postResponse;
    PlaceModel newPlace;
    TestBase testBase = new TestBase();

    @BeforeEach
    public void createPlace() {
        String baseUri = "https://rahulshettyacademy.com";
        String basePath = "/maps/api/place/";
        faker = new Faker();
        List<String> types = new ArrayList<>();
        testBase.buildRespSpec().statusCode(200);
        testBase.buildReqSpec(baseUri, basePath);
        types.add("shoe park");
        types.add("shop");
        PlaceModel.Location loc = new PlaceModel.Location();
        loc.setLat(-38.383494);
        loc.setLng(33.427362);

        newPlace = PlaceModel.builder()
                .location(loc)
                .language("French-IN")
                .name(String.format("%s %s", faker.name().firstName(), faker.name().lastName()))
                .website("http://google.com")
                .phoneNumber(faker.phoneNumber().cellPhone())
                .address(faker.address().streetAddress())
                .accuracy(faker.number().numberBetween(1, 100))
                .types(types)
                .build();

        postResponse = given()
                .when()
                .contentType(ContentType.JSON)
                .queryParam("key", "qaclick123")
                .when()
                .body(newPlace)
                .post("add/json");
    }

    @Test
    @DisplayName("Print response headers")
    public void test001() {
        JsonPath postJsonPathEvaluator = postResponse.jsonPath();
        placeId = postJsonPathEvaluator.get("place_id");

        given()
                .when()
                .queryParams("key", "qaclick123", "place_id", placeId)
                .when()
                .get("get/json")
                .then()
                .log()
                .headers()
                .statusCode(200);

    }

    @Test
    @DisplayName("Print response status line")
    public void test002() {
        JsonPath postJsonPathEvaluator = postResponse.jsonPath();
        placeId = postJsonPathEvaluator.get("place_id");

        given()
                .when()
                .queryParams("key", "qaclick123", "place_id", placeId)
                .when()
                .get("get/json")
                .then()
                .log()
                .status()
                .statusCode(200);

    }

    @Test
    @DisplayName("Print response body")
    public void test003() {
        JsonPath postJsonPathEvaluator = postResponse.jsonPath();
        placeId = postJsonPathEvaluator.get("place_id");

        given()
                .when()
                .queryParams("key", "qaclick123", "place_id", placeId)
                .when()
                .get("get/json")
                .then()
                .log()
                .body()
                .statusCode(200);

    }

    @Test
    @DisplayName("Print response body when request failed")
    public void test004() {
        given()
                .when()
                .queryParams("key", "qaclick123", "place_id", "1234")
                .when()
                .get("get/json")
                .then()
                .log()
                .ifError()
                .statusCode(200);

    }
}
