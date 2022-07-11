package api.models.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentOptions {
    @JsonProperty("allowed_payment_method")
    String allowedPaymentMethod;
}
