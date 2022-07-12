package api.tests.paypalexamples;

import api.models.paypal.*;
import api.tests.utils.PaypalTestBase;
import io.qameta.allure.Link;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@DisplayName("Paypal Api Tests Sample#1")
public class PostAsObjectTests extends PaypalTestBase {
    static String paymentId;

    @BeforeAll
    public static void setup() {
        generateToken();
    }

    @Test
    @DisplayName("Create paypal payment")
    @Order(1)
    public void createAPayment() {
        RedirectUrls red_url = RedirectUrls.builder()
                .cancelUrl("http://www.hawaii.com")
                .returnUrl("http://www.amazon.com")
                .build();

        Details details = Details.builder()
                .handlingFee("1.00")
                .shipping("0.03")
                .shippingDiscount("-1.00")
                .tax("0.07")
                .subtotal("30.00")
                .insurance("0.01")
                .build();

        Amount amount = Amount.builder()
                .currency("USD")
                .details(details)
                .total("30.11")
                .build();

        ShippingAddress shippingAddress = ShippingAddress.builder()
                .city("San Jose")
                .countryCode("US")
                .line1("4thFloor")
                .line2("unit34")
                .phone("011862212345678")
                .postal_code("95131")
                .recipientName("PAB")
                .state("CA")
                .build();

        Items firstItem = Items.builder()
                .currency("USD")
                .description("Brown color hat")
                .name("hat")
                .price("3")
                .quantity("5")
                .sku("1")
                .tax("0.01")
                .build();

        Items secondItem = Items.builder()
                .currency("USD")
                .description("Black color hand bag")
                .name("handbag")
                .price("15")
                .quantity("1")
                .sku("product34")
                .tax("0.02")
                .build();

        List<Items> items = new ArrayList<>();
        items.add(firstItem);
        items.add(secondItem);

        ItemList itemList = ItemList.builder()
                .shippingAddress(shippingAddress)
                .items(items)
                .build();

        PaymentOptions paymentOptions = PaymentOptions.builder()
                .allowedPaymentMethod("INSTANT_FUNDING_SOURCE").build();

        Transaction transaction = Transaction.builder()
                .itemList(itemList)
                .payment_options(paymentOptions)
                .custom("EBAY_EMS_90048630024435")
                .invoiceNumber("48787589674")
                .softDescriptor("ECHI5786786")
                .amount(amount)
                .description("This is the payment transaction description.")
                .build();

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = Payer.builder().paymentMethod("paypal").build();

        PostObject postObject = PostObject.builder()
                .intent("sale")
                .noteToPayer("Contact us")
                .payer(payer)
                .redirectUrls(red_url)
                .transactions(transactions)
                .build();

        paymentId = given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(accessToken)
                .when()
                .body(postObject)
                .post("/payments/payment")
                .then()
                .log()
                .all()
                .extract()
                .path("id");
    }

    @Test
    @DisplayName("Get Payment Details")
    @Order(2)
    public void getPaymentDetails() {
        given()
                .auth()
                .oauth2(accessToken)
                .when()
                .get("/payments/payment/" + paymentId)
                .then()
                .log()
                .all();
    }
}
