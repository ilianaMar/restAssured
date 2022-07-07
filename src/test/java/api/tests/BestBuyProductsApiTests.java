package api.tests;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class BestBuyProductsApiTests extends TestBase {
    static String jsonResponse;

    @BeforeAll
    public static void setup() {
        String baseUri = "http://localhost:3030/";
        String basePath = "";
        init(baseUri, basePath);
        respSpec.statusCode(200);
        jsonResponse = given().when().get("products").asString();
    }

    @Test
    @DisplayName("Get the root element")
    void testGetRootElement() {
        Map<String, ?> rootElement = JsonPath.read(jsonResponse, "$");
        System.out.println(rootElement.toString());
    }

    @Test
    @DisplayName("Get the total value of response")
    void testGetTotalResponseValue() {
        int total = JsonPath.read(jsonResponse, "$.total");
        System.out.println(total);
    }

    @Test
    @DisplayName("Test to get all products")
    void testGetAllProducts() {
        List<HashMap<String, Object>> data = JsonPath.read(jsonResponse, "$.data");
        data.forEach(System.out::println);
    }

    @Test
    @DisplayName("Test to get first product")
    void testGetFirstProduct() {
        Map<String, ?> firstDataElement = JsonPath.read(jsonResponse, "$.data[0]");
        System.out.println(firstDataElement);
    }

    @Test
    @DisplayName("Test to get last product")
    void testGetLastProduct() {
        Map<String, ?> lastDataElement = JsonPath.read(jsonResponse, "$.data[-1]");
        System.out.println(lastDataElement);
    }

    @Test
    @DisplayName("Test to get all ids of products")
    void testGetAllProductIds() {
        List<String> allProductIds = JsonPath.read(jsonResponse, "$.data[*].id");
        System.out.println(allProductIds);
    }

    @Test
    @DisplayName("Test to get all ids")
    void testGetAllIds() {
        List<String> allIds = JsonPath.read(jsonResponse, "$..[*].id");
        System.out.println(allIds.toString());
    }

    @Test
    @DisplayName("Get name of all products with price < 5")
    void testGetNameOfProductsWithPriceLessThanFive() {
        List<String> productNames = JsonPath.read(jsonResponse, "$.data[?(@.price < 5)].name");
        productNames.forEach(System.out::println);
    }


}
