package api.tests.bestBuyexamples;

import api.tests.utils.TestBase;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

@Epic("### Best Buy Api ###")
@Feature("Stores tests")
@DisplayName("Best Buy Stores Api Tests")
@Link("https://github.com/BestBuy/api-playground")
public class BestBuyStoresApiTests {
    static ValidatableResponse validatableResponse;
    TestBase testBase = new TestBase();

    @BeforeEach
    public void setup() {
        String baseUri = "http://localhost:3030/";
        String basePath = "";
        testBase.buildRespSpec().statusCode(200);
        testBase.buildReqSpec(baseUri, basePath);
        validatableResponse = given().when().get("stores").then();
    }

    @Test
    @DisplayName("Print total from response")
    @Story("Print total from response")
    void testGetTotal() {
        int total = validatableResponse.extract().path("total");
        System.out.println(total);
    }

    @Test
    @DisplayName("Get store name from first element")
    @Story("Get store name from first element")
    void testGetFirstElementName() {
        String firstName = validatableResponse.extract().path("data[0].name");
        System.out.println(firstName);
    }

    @Test
    @DisplayName("Test to get first store service name")
    @Story("Test to get first store service name")
    void testGetFirstServiceName() {
        String firstServiceName = validatableResponse.extract().path("data[0].services[0].name");
        System.out.println(firstServiceName);
    }

    @Test
    @DisplayName("Get info for zip 55901")
    @Story("Get info for zip 55901")
    void testGetStoreByZip() {
        Map<String, ?> service = validatableResponse.extract().path("data.find{it.zip=='55901'}");
        System.out.println(service);

    }

    @Test
    @DisplayName("Get address for zip 55901")
    @Story("Get address for zip 55901")
    void testGetAddressByZip() {
        String serviceAddress = validatableResponse.extract().path("data.find{it.zip=='55901'}.address");
        System.out.println(serviceAddress);
    }

    @Test
    @DisplayName("Get all info for stores with max and min id")
    @Story("Get all info for stores with max and min id")
    void testGetStoreInfoWithMaxAndMinId() {
        Map<String, ?> storeWithMaxId = validatableResponse.extract().path("data.max{it.id}");
        Map<String, ?> storeWithMinId = validatableResponse.extract().path("data.min{it.id}");
        System.out.println(storeWithMaxId);
        System.out.println(storeWithMinId);
    }

    @Test
    @DisplayName("Get store zip code for stores with id less 10")
    @Story("Get store zip code for stores with id less 10")
    void testGetStoreWithIdLessTen() {
        List<String> storeZips = validatableResponse.extract().path("data.findAll{it.id < 10}.zip");
        storeZips.forEach(System.out::println);
    }

    @Test
    @DisplayName("Get all store services names")
    @Story("Get all store services names")
    void testGetAllServicesNames() {
        List<List<String>> storeServicesNames = validatableResponse.extract().path("data.services.findAll{it.name}.name");
        storeServicesNames.forEach(System.out::println);
    }
}
