package pojo.createBooking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Bookingdates{

    @JsonProperty("checkin")
    public String checkin;

    @JsonProperty("checkout")
    public String checkout;
}