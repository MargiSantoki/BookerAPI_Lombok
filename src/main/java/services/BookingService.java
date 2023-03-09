package services;

import base.BaseService;
import constants.PathConstant;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class BookingService extends BaseService {

    public Response getReport(){
        return executeApi("GET", PathConstant.BOOKING);
    }

    public Response getReportById(int bookingId){
        Map<String,Object> bookingIdPathParam = new HashMap<>();
        bookingIdPathParam.put("bookingId",bookingId);
        setPathParameter(bookingIdPathParam);
        return executeApi("GET", PathConstant.BOOKING + "/{bookingId}");
    }

    public Response createBooking(Object payload){
        setBody(payload);
        return executeApi("POST", PathConstant.BOOKING);
    }

    public Response updateBooking(String token, int bookingId, Object payload){
        setBody(payload);
        setAuthentication(token);

        Map<String,Object> bookingIdPathParam = new HashMap<>();
        bookingIdPathParam.put("bookingId",bookingId);
        setPathParameter(bookingIdPathParam);

        Map<String,String> cookie = new HashMap<>();
        cookie.put("token",token);
        setCookie(cookie);

        return executeApi("PUT", PathConstant.BOOKING + "/{bookingId}");
    }

    public Response partialUpdateBooking(String token, int bookingId, Object payload){
        setBody(payload);
        setAuthentication(token);

        Map<String,Object> bookingIdPathParam = new HashMap<>();
        bookingIdPathParam.put("bookingId",bookingId);
        setPathParameter(bookingIdPathParam);

        Map<String,String> cookie = new HashMap<>();
        cookie.put("token",token);
        setCookie(cookie);

        return executeApi("PATCH", PathConstant.BOOKING + "/{bookingId}");
    }

    public Response deleteBooking(String token, int bookingId){
        setAuthentication(token);

        Map<String,String> cookie = new HashMap<>();
        cookie.put("token",token);
        setCookie(cookie);

        return executeApi("DELETE", PathConstant.BOOKING + "/" + bookingId);
    }
}
