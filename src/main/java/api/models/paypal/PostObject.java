package api.models.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PostObject {
    List<Transaction> transactions;
    @JsonProperty("note_to_payer")
    String noteToPayer;
    String intent;
    Payer payer;
    @JsonProperty("redirect_urls")
    RedirectUrls redirectUrls;
}
