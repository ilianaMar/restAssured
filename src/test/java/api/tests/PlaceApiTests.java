package api.tests;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;

import java.util.*;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class PlaceApiTests extends TestBase {
    String placeId, payload;
    Faker faker;
    Response postResponse;
    Map<String, Object> payloadData;
    String[] types;
    Gson gson = new Gson();

    @BeforeAll
    public static void setup() {
        String baseUri = "https://rahulshettyacademy.com";
        String basePath = "/maps/api/place/";
        init(baseUri, basePath);
    }

    @BeforeEach
    public void createPlace() {
        faker = new Faker();
        types = new String[]{"shoe park", "shop"};

        payloadData = new HashMap<>();
        Map<String, Object> locations = new HashMap<>();
        locations.put("lat", -38.383494);
        locations.put("lng", 33.427362);
        payloadData.put("location", locations);
        payloadData.put("accuracy", faker.number().numberBetween(1, 100));
        payloadData.put("name", String.format("%s %s", faker.name().firstName(), faker.name().lastName()));
        payloadData.put("phone_number", faker.phoneNumber().cellPhone());
        payloadData.put("address", faker.address().streetAddress());
        payloadData.put("types", types);
        payloadData.put("website", "http://google.com");
        payloadData.put("language", "French-IN");

        payload = gson.toJson(payloadData);

        System.out.println(payload);
        postResponse = given()
                .when()
                .contentType(ContentType.JSON)
                .queryParam("key", "qaclick123")
                .when()
                .body(payload)
                .post("add/json");
    }

    @Test
    @DisplayName("Test to create a new place")
    void testWithPostRequest() {
        postResponse.then().spec(respSpec);
        JsonPath postJsonPathEvaluator = postResponse.jsonPath();
        placeId = postJsonPathEvaluator.get("place_id");
        postResponse.prettyPrint();

        Response getResponse = given()
                .when()
                .queryParams("key", "qaclick123", "place_id", placeId)
                .when()
                .get("get/json");

        getResponse.then().statusCode(200);
        getResponse.prettyPrint();

        JsonPath getJsonPathEvaluator = getResponse.jsonPath();
        assertEquals(Integer.parseInt(getJsonPathEvaluator.get("accuracy")), payloadData.get("accuracy"));
        assertEquals(getJsonPathEvaluator.get("name"), payloadData.get("name"));
        assertEquals(getJsonPathEvaluator.get("phone_number"), payloadData.get("phone_number"));
        assertEquals(getJsonPathEvaluator.get("address"), payloadData.get("address"));
        assertEquals(getJsonPathEvaluator.get("types"), String.format("%s,%s", types[0], types[1]));
        assertEquals(getJsonPathEvaluator.get("website"), payloadData.get("website"));
        assertEquals(getJsonPathEvaluator.get("language"), payloadData.get("language"));
    }

    @Test
    @DisplayName("Test to delete a created place")
    void testWithDeleteRequest() {
        postResponse.then().statusCode(200);
        JsonPath postJsonPathEvaluator = postResponse.jsonPath();
        placeId = postJsonPathEvaluator.get("place_id");
        Map<String, Object> deleteData = new HashMap<>();
        deleteData.put("place_id", placeId);

        Response deleteResponse = given()
                .when()
                .body(gson.toJson(deleteData))
                .delete("delete/json");
        deleteResponse.then()
                .statusCode(200)
                .assertThat().body("status", equalTo("OK"));
        deleteResponse.prettyPrint();

        Response getResponse = given()
                .when()
                .queryParams("key", "qaclick123", "place_id", placeId)
                .when()
                .get("get/json");
        getResponse.then()
                .statusCode(404)
                .assertThat()
                .body("msg", equalTo("Get operation failed, looks like place_id  doesn't exists"));
        getResponse.prettyPrint();
    }

    @Test
    @DisplayName("Test to update a created place")
    void testWithUpdateRequest() {
        postResponse.then().statusCode(200);
        JsonPath postJsonPathEvaluator = postResponse.jsonPath();
        placeId = postJsonPathEvaluator.get("place_id");
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("place_id", placeId);
        updateData.put("address", faker.address().streetAddress());
        updateData.put("key", "qaclick123");

        Response putResponse = given()
                .when()
                .body(gson.toJson(updateData))
                .put("update/json");
        putResponse.then()
                .statusCode(200)
                .assertThat().body("msg", equalTo("Address successfully updated"));
        putResponse.prettyPrint();

        Response getResponse = given()
                .when()
                .queryParams("key", "qaclick123", "place_id", placeId)
                .when()
                .get("get/json");
        getResponse.then()
                .statusCode(200)
                .assertThat().body("address", equalTo(updateData.get("address")));
    }
}
