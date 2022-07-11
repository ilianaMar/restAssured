package api.loggingexamples;

import api.models.PlaceModel;
import api.tests.TestBase;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class LoggingRequestValuesTests extends TestBase {
    String placeId;
    Faker faker;
    Response postResponse;
    PlaceModel newPlace;

    @BeforeAll
    public static void setup() {
        String baseUri = "https://rahulshettyacademy.com";
        String basePath = "/maps/api/place/";
        init(baseUri, basePath);
    }

    @BeforeEach
    public void createPlace() {
        faker = new Faker();
        List<String> types = new ArrayList<>();
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
    @DisplayName("Print request headers")
    public void test001() {
        postResponse.then().spec(respSpec);
        JsonPath postJsonPathEvaluator = postResponse.jsonPath();
        placeId = postJsonPathEvaluator.get("place_id");

        given()
                .log()
                .headers()
                .when()
                .queryParams("key", "qaclick123", "place_id", placeId)
                .when()
                .get("get/json")
                .then()
                .statusCode(200);

    }

    @Test
    @DisplayName("Print request params")
    public void test002() {
        postResponse.then().spec(respSpec);
        JsonPath postJsonPathEvaluator = postResponse.jsonPath();
        placeId = postJsonPathEvaluator.get("place_id");

        given()
                .log()
                .parameters()
                .when()
                .queryParams("key", "qaclick123", "place_id", placeId)
                .when()
                .get("get/json")
                .then()
                .statusCode(200);

    }

    @Test
    @DisplayName("Print request body")
    public void test003() {
        postResponse.then().spec(respSpec);
        JsonPath postJsonPathEvaluator = postResponse.jsonPath();
        placeId = postJsonPathEvaluator.get("place_id");

        PlaceModel updatePlace = PlaceModel.builder()
                .placeId(placeId)
                .address(faker.address().streetAddress())
                .key("qaclick123")
                .build();

        given()
                .contentType(ContentType.JSON)
                .log()
                .body()
                .when()
                .body(updatePlace)
                .put("update/json")
                .then()
                .statusCode(200)
                .assertThat().body("msg", equalTo("Address successfully updated"));

    }

    @Test
    @DisplayName("Print request all details")
    public void test004() {
        postResponse.then().spec(respSpec);
        JsonPath postJsonPathEvaluator = postResponse.jsonPath();
        placeId = postJsonPathEvaluator.get("place_id");

        PlaceModel updatePlace = PlaceModel.builder()
                .placeId(placeId)
                .address(faker.address().streetAddress())
                .key("qaclick123")
                .build();

        given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .body(updatePlace)
                .put("update/json")
                .then()
                .statusCode(200)
                .assertThat().body("msg", equalTo("Address successfully updated"));

    }

    @Test
    @DisplayName("Print request all details if request is failed")
    public void test005() {
        postResponse.then().spec(respSpec);
        JsonPath postJsonPathEvaluator = postResponse.jsonPath();
        placeId = postJsonPathEvaluator.get("place_id");

        PlaceModel updatePlace = PlaceModel.builder()
                .placeId(placeId)
                .address(faker.address().streetAddress())
                .key("qaclick123")
                .build();

        given()
                .contentType(ContentType.JSON)
                .log()
                .ifValidationFails()
                .when()
                .body(updatePlace)
                .put("update/json")
                .then()
                .statusCode(500);
    }
}
