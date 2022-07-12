# Rest Assured Examples

### FOOTBALL API TESTS
 - Path src/test/java/api/tests/footballexamples/FootballApiTests.java
 - Used libraries: 
   - Static methods of org.hamcrest.Matchers.* for assertions
   - Static methods of io.restassured.RestAssured.*
   - io.restassured.response.Response

### PLACE API TESTS
- Path  src/test/java/api/tests/placeexamples/PlaceApiTests.java
- Use libraries:
    - Static methods of org.hamcrest.Matchers.* for assertions
    - Static methods of io.restassured.RestAssured.*
    - io.restassured.response.Response
- Used POJO to build response payload

### BESTBUY API TESTS
- Preconditions:
   - Clone repo using the guidelines https://github.com/BestBuy/api-playground
   - Start the application
- Path src/test/java/api/tests/bestBuyexamples/BesBuyStoresApiTests.java
- Used libraries:
    - Static methods of org.hamcrest.Matchers.* for assertions
    - Json path of restassure for Json extractions of response body
- Path  src/test/java/api/tests/bestBuyexamples/BesBuyProductsApiTests.java
- Used libraries:
    - Static methods of org.hamcrest.Matchers.* for assertions
    - com.jayway.jsonpath.JsonPath for Json extractions of response body

### TRELLO API TESTS
- Preconditions: 
   - Create trello account and generate key, secret and token
   - Create a file called trello-auth.json and use template src/test/java/api/config/trello-auth.json.dist to add 
     authorisation data
   - Create some dashboard from UI to generate test data
- Path src/test/java/api/trelloexamples/TrelloApiTests.java:  Sample with key and token headers authorization
  and Oauth 1.0 authorization
- Used libraries:
    - Static methods of org.hamcrest.Matchers.* for assertions
    - Static methods of io.restassured.RestAssured.*
    - io.restassured.response.Response
    - io.restassured.filter.log.RequestLoggingFilter and io.restassured.filter.log.ResponseLoggingFilter
    - org.skyscreamer.jsonassert.JSONAssert
    - org.skyscreamer.jsonassert.JSONCompareMode


### PAYPAL API TESTS
- Preconditions:
    - Create paypal developer account and generate client_id and client_secret
    - Create a file called paypal-auth.json and use template src/test/java/api/config/paypal-auth.json.dist to add
      authorisation data
- Path src/test/java/api/paypalexamples/*
- Used libraries:
    - Static methods of io.restassured.RestAssured.*
    - io.restassured.response.Response
- Used POJO to build response payload

### ALLURE REPORTS
- Install locally allure depends on your find information https://docs.qameta.io/allure/#_installing_a_commandline
- Delete target directory and run mvn clean test
- Open terminal and open target directory 
- Run command *allure serve allure-results* in terminal to build report graphs 
