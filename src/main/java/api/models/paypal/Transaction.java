package api.models.paypal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Transaction {
    Amount amount;
    PaymentOptions payment_options;
    ItemList item_list;
    String description, soft_descriptor, invoice_number, custom;
}
