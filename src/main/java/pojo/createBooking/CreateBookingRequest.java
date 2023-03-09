package pojo.createBooking;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateBookingRequest {
    public String firstname;
    public String lastname;
    public int totalprice;
    public boolean depositpaid;
    public Bookingdates bookingdates;
    public String additionalneeds;
}
