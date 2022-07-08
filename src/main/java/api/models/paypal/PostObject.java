package api.models.paypal;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PostObject {
    List<Transaction> transactions;
    String note_to_payer, intent;
    Payer payer;
    RedirectUrls redirect_urls;
}
