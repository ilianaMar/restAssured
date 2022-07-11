package api.models.paypal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Payer {
    String payment_method;
}
