package api.models.paypal;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ItemList {
    private ShippingAddress shipping_address;
    private List<Items> items;
}
