package api.models.paypal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Details {
    String shipping, shipping_discount, tax, handling_fee, subtotal, insurance;
}
