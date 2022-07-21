package api.tests.footballexamples;

import static io.restassured.RestAssured.*;

import api.tests.utils.JsonHelper;
import api.tests.utils.TestBase;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.hamcrest.Matchers.*;

@DisplayName("Football Api Tests")
public class FootballApiTests {
    TestBase testBase = new TestBase();
    private final String urlPath = "src/test/java/api/tests/config/basic-urls.json";
    private final Map<?, ?> basicUrlData = new JsonHelper(urlPath).getJson();
    private final Object footballBaseUrl = basicUrlData.get("footballUrl");

    @BeforeEach
    public void setup() {
        String baseUri = footballBaseUrl.toString();
        String basePath = "/v4/";
        testBase.buildReqSpec(baseUri, basePath);
        testBase.buildRespSpec().statusCode(200);
    }

    @Test
    @DisplayName("Test with get request")
    void testWithGetMethod() {
        Response response = given().get("matches");
        response.prettyPrint();

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDate = dtf.format(today);
        String endDate = dtf.format(tomorrow);
        System.out.println(startDate);
        System.out.println(endDate);

        given()
                .get("matches")
                .then()
                .assertThat()
                .body("filters.dateFrom", equalTo(startDate), "filters.dateTo", equalTo(endDate));
    }

    @Test
    @DisplayName("Get data by querying parameter")
    void testGetMethodById() {
        Response resp = given().queryParam("areas", 2202).when().get("competitions");
        resp.prettyPrint();
        resp.then().assertThat().body("filters.areas[0]", equalTo(2202));
    }

    @Test
    @DisplayName("Path parameter example")
    void testGetSingleArea() {
        int id = 2202;

        Response resp = given().pathParam("id", id).when().log().all().get("areas/{id}");
        resp.prettyPrint();
        resp.then().assertThat().body("id", equalTo(id),
                "name", equalTo("Sápmi"),
                "code", equalTo("SAP"),
                "parentAreaId", equalTo(2267),
                "parentArea", equalTo("World"));
    }
}
