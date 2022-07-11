package api.models.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Payer {
    @JsonProperty("payment_method")
    String paymentMethod;
}
