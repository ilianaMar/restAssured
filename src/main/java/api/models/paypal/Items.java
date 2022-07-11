package api.models.paypal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Items {
    String price, tax, description, name, quantity, sku, currency;
}
