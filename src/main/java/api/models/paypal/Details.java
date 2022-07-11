package api.models.paypal;

import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Builder
@Data
public class Details {
    String shipping, tax, subtotal, insurance;

    @JsonProperty("shipping_discount")
    String shippingDiscount;

    @JsonProperty("handling_fee")
    String handlingFee;
}
