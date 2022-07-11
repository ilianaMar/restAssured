package api.models.paypal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentOptions {
    String allowed_payment_method;
}
