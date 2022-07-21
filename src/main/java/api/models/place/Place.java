package api.models.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@Data
public class Place {
    String name, address, website, language, key;
    @JsonProperty("phone_number")
    String phoneNumber;
    @JsonProperty("place_id")
    String placeId;
    List<String> types;
    Location location;
    int accuracy;

}
