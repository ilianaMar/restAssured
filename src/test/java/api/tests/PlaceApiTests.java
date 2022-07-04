package api.tests;

import api.models.PlaceModel;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;

import java.util.*;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class PlaceApiTests extends TestBase {
    String placeId;
    Faker faker;
    Response postResponse;
    Map<String, Object> payloadData;
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

//        payloadData = new HashMap<>();
//        Map<String, Object> locations = new HashMap<>();
//        locations.put("lat", -38.383494);
//        locations.put("lng", 33.427362);
//        payloadData.put("location", locations);
//        payloadData.put("accuracy", faker.number().numberBetween(1, 100));
//        payloadData.put("name", String.format("%s %s", faker.name().firstName(), faker.name().lastName()));
//        payloadData.put("phone_number", faker.phoneNumber().cellPhone());
//        payloadData.put("address", faker.address().streetAddress());
//        payloadData.put("types", types);
//        payloadData.put("website", "http://google.com");
//        payloadData.put("language", "French-IN");

        PlaceModel.Location loc = new PlaceModel.Location();
        loc.setLat(-38.383494);
        loc.setLng(33.427362);

        newPlace = PlaceModel.builder()
                .location(loc)
                .language("French-IN")
                .name(String.format("%s %s", faker.name().firstName(), faker.name().lastName()))
                .website("http://google.com")
                .phone_number(faker.phoneNumber().cellPhone())
                .address(faker.address().streetAddress())
                .accuracy(faker.number().numberBetween(1, 100))
                .types(types)
                .build();

        System.out.println("11111 " + newPlace);
        postResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .queryParam("key", "qaclick123")
                .when()
                .body(newPlace)
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
                .contentType(ContentType.JSON)
                .when()
                .queryParams("key", "qaclick123", "place_id", placeId)
                .when()
                .get("get/json");

        getResponse.then().statusCode(200);
        getResponse.prettyPrint();

        JsonPath getJsonPathEvaluator = getResponse.jsonPath();

//        using POJO
        assertEquals(Integer.parseInt(getJsonPathEvaluator.get("accuracy")), newPlace.getAccuracy());
        assertEquals(getJsonPathEvaluator.get("name"), newPlace.getName());
        assertEquals(getJsonPathEvaluator.get("phone_number"), newPlace.getPhone_number());
        assertEquals(getJsonPathEvaluator.get("address"), newPlace.getAddress());
        assertEquals(getJsonPathEvaluator.get("types"), String.format("%s,%s", newPlace.getTypes().get(0), newPlace.getTypes().get(1)));
        assertEquals(getJsonPathEvaluator.get("website"), newPlace.getWebsite());
        assertEquals(getJsonPathEvaluator.get("language"), newPlace.getLanguage());

//        using hash map
//        assertEquals(Integer.parseInt(getJsonPathEvaluator.get("accuracy")), payloadData.get("accuracy"));
//        assertEquals(getJsonPathEvaluator.get("name"), payloadData.get("name"));
//        assertEquals(getJsonPathEvaluator.get("phone_number"), payloadData.get("phone_number"));
//        assertEquals(getJsonPathEvaluator.get("address"), payloadData.get("address"));
//        assertEquals(getJsonPathEvaluator.get("types"), String.format("%s,%s", types[0], types[1]));
//        assertEquals(getJsonPathEvaluator.get("website"), payloadData.get("website"));
//        assertEquals(getJsonPathEvaluator.get("language"), payloadData.get("language"));
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
                .contentType(ContentType.JSON)
                .when()
                .body(deleteData)
                .delete("delete/json");
        deleteResponse.then()
                .statusCode(200)
                .assertThat().body("status", equalTo("OK"));
        deleteResponse.prettyPrint();

        Response getResponse = given()
                .contentType(ContentType.JSON)
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

//        using  hashMap
//        Map<String, Object> updateData = new HashMap<>();
//        updateData.put("place_id", placeId);
//        updateData.put("address", faker.address().streetAddress());
//        updateData.put("key", "qaclick123");

        PlaceModel updatePlace = PlaceModel.builder()
                .place_id(placeId)
                .address(faker.address().streetAddress())
                .key("qaclick123")
                .build();

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(updatePlace)
                .put("update/json")
                .then()
                .statusCode(200)
                .assertThat().body("msg", equalTo("Address successfully updated"));

        Response getResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .queryParams("key", "qaclick123", "place_id", placeId)
                .when()
                .get("get/json");
        getResponse.then()
                .statusCode(200)
//                .assertThat().body("address", equalTo(updateData.get("address")))
                .assertThat().body("address", equalTo(updatePlace.getAddress()));
    }
}
