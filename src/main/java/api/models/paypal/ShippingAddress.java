package api.models.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ShippingAddress {
    String postal_code, state, line1, city, line2, phone;
    @JsonProperty("recipient_name")
    String recipientName;
    @JsonProperty("country_code")
    String countryCode;
}
