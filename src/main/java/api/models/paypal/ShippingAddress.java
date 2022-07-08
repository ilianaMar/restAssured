package api.models.paypal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ShippingAddress {
    String postal_code, state, line1, recipient_name, country_code, city, line2, phone;
}
