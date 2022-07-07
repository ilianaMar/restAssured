package api.tests;

import com.google.gson.Gson;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TrelloApiTests extends TestBase {
    private static Object apiKey = null;
    private static Object apiToken = null;

    @BeforeAll
    public static void setup() {
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("src/test/java/api/config/trello-auth.json"));

            // convert JSON file to map
            Map<?, ?> authData = gson.fromJson(reader, Map.class);
            apiKey = authData.get("key");
            apiToken = authData.get("token");

            // close reader
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String baseUri = "https://api.trello.com/";
        String basePath = "1/members/me/";
        init(baseUri, basePath);
        respSpec.statusCode(200);
        reqSpec.queryParams("fields", "name,url", "key", apiKey, "token", apiToken);
    }

    @Nested
    @DisplayName("Get all boards with trello api key and token")
    class UseRestAssuredJsonPathTests {
        ExtractableResponse<Response> response;

        @BeforeEach
        void invokeGetApi() {
            response = given()
                    .when()
                    .get("boards")
                    .then()
                    .extract();
        }

        @Test
        @DisplayName("Get all boards ids")
        void getUserBoardIds() {
            List<String> ids = response.path("id");
            System.out.println("all ids " + ids);
        }

        @Test
        @DisplayName("Get all boards names")
        void getUserBoardNames() {
            List<String> names = response.path("name");
            System.out.println("all names " + names);
        }

        @Test
        @DisplayName("Get all boards urls")
        void getUserBoardUrls() {
            List<String> urls = response.path("url");
            System.out.println("all urls " + urls);
        }

        @Test
        @DisplayName("Get first board id")
        void getFirstBoardId() {
            String firstId = response.path("[0].id");
            System.out.println("first id " + firstId);
        }

        @Test
        @DisplayName("Get first board name")
        void getFirstBoardName() {
            String firstName = response.path("[0].name");
            System.out.println("first name " + firstName);
        }

        @Test
        @DisplayName("Get first board url")
        void getFirstBoardUrl() {
            String firstUrl = response.path("[0].url");
            System.out.println("first url " + firstUrl);
        }

        @Test
        @DisplayName("Get boards count")
        void getFirstBoardsCount() {
            int size = response.path("size()");
            System.out.println("board size " + size);
        }

        @Test
        @DisplayName("Get board with name 'Iliana Board'")
        void getAllBoardsByName() {
            List<Map<String, ?>> boards = response.path("findAll { a -> a.name == 'Iliana Board' }");
            System.out.println("board with name 'Iliana Board' " + boards);
        }

        @Test
        @DisplayName("Get board with name which contains 2")
        void getBoardByContainsName() {
            List<Map<String, ?>> boards = response.path("findAll { a -> a.name.contains('2') }");
            System.out.println("board with name which contains 2 " + boards);
        }

        @Test
        @DisplayName("Get board ids with name 'Iliana'")
        void getIdOfAllBoardsByName() {
            List<String> boards = response.path("findAll { a -> a.name.contains('Iliana') }.id");
            System.out.println("board with name which contains 'Iliana' " + boards);
        }

        @Test
        @DisplayName("Get board ids with name which starts with 'Ili'")
        void getIdOfAllBoardsByStartName() {
            List<String> boards = response.path("findAll { a -> a.name ==~/Ili.*/ }.id");
            System.out.println("board with name which start with 'Ili' " + boards);
        }

        @Test
        @DisplayName("Get board ids with name which ends with '3'")
        void getIdOfAllBoardsByEndName() {
            List<String> boards = response.path("findAll { a -> a.name ==~/.*3/ }.id");
            System.out.println("board with name which ends with '3' " + boards);
        }
    }

    @Nested
    @DisplayName("use harmcrest matchers")
    class UseHarmcrestMatchersTests {
        Response response;

        @BeforeEach
        void invokeGetApi() {
            response = given()
                    .when()
                    .get("boards");
        }

        @Test
        @DisplayName("#1")
        void assertBoardsCount() {
            response.then()
                    .assertThat()
                    .body("size()", equalTo(3));
        }

        @Test
        @DisplayName("#2")
        void assertFirstBoardName() {
            response.then()
                    .assertThat()
                    .body("[0].name", equalTo("Iliana Board"));
        }

        @Test
        @DisplayName("#3")
        void assertFirstBoardName2() {
            response.then()
                    .assertThat()
                    .body("[0].name", containsString("Board"));
        }

        @Test
        @DisplayName("#4")
        void assertKeyValue() {
            response.then()
                    .assertThat()
                    .body("[0]", hasKey("name"), "[0]", hasValue("Iliana Board"))
                    .body("[0].name", startsWith("Ili"))
                    .body("[1].name", endsWith("2"))
                    .body("[2].name", endsWith("3"))
                    .statusCode(200);
        }

        @Test
        @DisplayName("#5")
        void assertHasItems() {
            response.then()
                    .assertThat()
                    .body("findAll { a -> a.name == 'Iliana Board' }", hasItems(hasEntry("name", "Iliana Board")))
                    .body("findAll { a -> a.name == 'Iliana Board' }", hasItems(hasEntry("id", "62c6a687a9d6885e2bae4ae1")))
                    .body("findAll { a -> a.name == 'Iliana Board' }", hasItems(hasEntry("url", "https://trello.com/b/CU75aizD/iliana-board")))
                    .statusCode(200);
        }

        @Test
        @DisplayName("#6")
        void logicalAssertions() {
            response.then()
                    .assertThat()
                    .body("size()", equalTo(3))
                    .body("size()", greaterThan(2))
                    .body("size()", lessThan(5))
                    .body("size()", greaterThanOrEqualTo(3))
                    .body("size()", lessThanOrEqualTo(3))
                    .statusCode(200);
        }
    }

    @Nested
    @DisplayName("use json assert method")
    class UseJsonAssertTests {
        Response response;

        @BeforeEach
        void invokeGetApi() {
            response = given()
                    .when()
                    .get("boards");
        }

        @DisplayName("#6")
        @Test
        void successfulJsonAssert() throws JSONException {
            String expected = "[\n" +
                    "    {\n" +
                    "        \"id\": \"62c6a687a9d6885e2bae4ae1\",\n" +
                    "        \"name\": \"Iliana Board\",\n" +
                    "        \"url\": \"https://trello.com/b/CU75aizD/iliana-board\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"id\": \"62c6aedff12b6256ed00dc46\",\n" +
                    "        \"name\": \"Iliana Board 2\",\n" +
                    "        \"url\": \"https://trello.com/b/TsutTYcV/iliana-board-2\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"id\": \"62c6aeeda9fe6c34a66be096\",\n" +
                    "        \"name\": \"Iliana Board 3\",\n" +
                    "        \"url\": \"https://trello.com/b/F18S4CUY/iliana-board-3\"\n" +
                    "    }\n" +
                    "]";

            JSONAssert.assertEquals(expected, response.asString(), JSONCompareMode.LENIENT);
        }

        @DisplayName("#7")
        @Test
        void failedJsonAssert() throws JSONException {
            String expected = "[\n" +
                    "    {\n" +
                    "        \"id\": \"62c6a687a9d6885e2bae4ae1\",\n" +
                    "        \"name\": \"Iliana Board\",\n" +
                    "        \"url\": \"https://trello.com/b/CU75aizD/iliana-board\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"id\": \"62c6aedff12b6256ed00dc46\",\n" +
                    "        \"name\": \"Iliana Board 10\",\n" +
                    "        \"url\": \"https://trello.com/b/TsutTYcV/iliana-board-2\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"id\": \"62c6aeeda9fe6c34a66be096\",\n" +
                    "        \"name\": \"Iliana Board 3\",\n" +
                    "        \"url\": \"https://trello.com/b/F18S4CUY/iliana-board-3\"\n" +
                    "    }\n" +
                    "]";

            JSONAssert.assertEquals(expected, response.asString(), JSONCompareMode.LENIENT);
        }
    }
}
