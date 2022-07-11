package api.models.paypal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Amount {
    String total;
    String currency;
    Details details;
}
