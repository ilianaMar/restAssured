package api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@Data
public class PlaceModel {
    @Data
    public static class Location {
        double lat, lng;
    }

    String name, address, website, language, key;
    @JsonProperty("phone_number")
    String phoneNumber;
    @JsonProperty("place_id")
    String placeId;
    List<String> types;
    public Location location;
    int accuracy;

}
