package api.models.paypal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RedirectUrls {
    String cancel_url, return_url;
}
