package api.models.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RedirectUrls {
    @JsonProperty("cancel_url")
    String cancelUrl;
    @JsonProperty("redirect_url")
    String returnUrl;
}
