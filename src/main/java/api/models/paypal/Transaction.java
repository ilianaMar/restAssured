package api.models.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Transaction {
    Amount amount;
    PaymentOptions payment_options;
    ItemList itemList;
    String description, custom;
    @JsonProperty("soft_descriptor")
    String softDescriptor;
    @JsonProperty("invoice_number")
    String invoiceNumber;
}
