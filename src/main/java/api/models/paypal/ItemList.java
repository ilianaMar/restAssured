package api.models.paypal;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ItemList {
    private ShippingAddress shippingAddress;
    private List<Items> items;
}
