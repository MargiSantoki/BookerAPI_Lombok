package testscripts;

import constants.StatusCode;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import pojo.createBooking.Bookingdates;
import pojo.createBooking.CreateBookingRequest;
import pojo.response.CreateBookingResponse;
import services.BookingService;
import services.GenerateTokenService;
import utility.DataGenerator;

public class BookingTest {

    Response response;
    BookingService bookingService = new BookingService();
    int bookingId;
    SoftAssert softAssert = new SoftAssert();
    String token;
    GenerateTokenService generateTokenService = new GenerateTokenService();

    @BeforeMethod
    public void tokenGeneration(){
        token = generateTokenService.createToken();
    }

    @AfterMethod
    public void restAttributes(){
        generateTokenService.removeAttributes();
    }

    @Test (priority = 1)
    public void getBookingIdsTest(){
        response = bookingService.getReport();
        Assert.assertEquals(response.getStatusCode(), StatusCode.OK);
        Assert.assertTrue(response.jsonPath().getList("bookingid").size() > 0);
    }

    @Test (priority = 2)
    public void createBookingTest(){
        Bookingdates bookingdates = Bookingdates.builder()
                .checkin("2023-03-07")
                .checkout("2023-03-10")
                .build();

        CreateBookingRequest createBookingRequest = CreateBookingRequest.builder()
                .firstname(DataGenerator.getFirstName())
                .lastname(DataGenerator.getLastName())
                .totalprice(DataGenerator.getPrice(5))
                .depositpaid(DataGenerator.getBoolean())
                .bookingdates(bookingdates)
                .additionalneeds(DataGenerator.getAdditionalNeeds())
                .build();

        response = bookingService.createBooking(createBookingRequest);
        Assert.assertEquals(response.getStatusCode(),StatusCode.OK);
        bookingId = response.jsonPath().getInt("bookingid");
        Assert.assertTrue(bookingId > 0);

        CreateBookingResponse createBookingResponse = response.as(CreateBookingResponse.class);
        softAssert.assertEquals(createBookingResponse.booking.firstname,createBookingRequest.firstname);
        softAssert.assertEquals(createBookingResponse.booking.lastname,createBookingRequest.lastname);
        softAssert.assertEquals(createBookingResponse.booking.totalprice,createBookingRequest.totalprice);
        softAssert.assertEquals(createBookingResponse.booking.depositpaid,createBookingRequest.depositpaid);
        softAssert.assertEquals(createBookingResponse.booking.bookingdates.checkin,createBookingRequest.bookingdates.checkin);
        softAssert.assertEquals(createBookingResponse.booking.bookingdates.checkout,createBookingRequest.bookingdates.checkout);
        softAssert.assertEquals(createBookingResponse.booking.additionalneeds,createBookingRequest.additionalneeds);
        softAssert.assertAll();
    }

    @Test (priority = 3)
    public void getBookingByIdTest(){
        response = bookingService.getReportById(bookingId);
        Assert.assertEquals(response.getStatusCode(), StatusCode.OK);
    }

    @Test (priority = 4)
    public void updateBookingTest(){
        Bookingdates bookingdates = Bookingdates.builder()
                .checkin("2023-03-10")
                .checkout("2023-03-15")
                .build();

        CreateBookingRequest requestPayload = CreateBookingRequest.builder()
                .firstname(DataGenerator.getFirstName())
                .lastname(DataGenerator.getLastName())
                .totalprice(DataGenerator.getPrice(5))
                .depositpaid(DataGenerator.getBoolean())
                .bookingdates(bookingdates)
                .additionalneeds(DataGenerator.getAdditionalNeeds())
                .build();

        response = bookingService.updateBooking(token, bookingId, requestPayload);
        Assert.assertEquals(response.getStatusCode(),StatusCode.OK);

        pojo.updatingResponse.CreateBookingRequest responsePayload = response.as(pojo.updatingResponse.CreateBookingRequest.class);
        softAssert.assertEquals(responsePayload.firstname, requestPayload.firstname);
        softAssert.assertEquals(responsePayload.lastname, requestPayload.lastname);
        softAssert.assertEquals(responsePayload.totalprice, requestPayload.totalprice);
        softAssert.assertEquals(responsePayload.depositpaid, requestPayload.depositpaid);
        softAssert.assertEquals(responsePayload.bookingdates.checkin, requestPayload.bookingdates.checkin);
        softAssert.assertEquals(responsePayload.bookingdates.checkout, requestPayload.bookingdates.checkout);
        softAssert.assertEquals(responsePayload.additionalneeds, requestPayload.additionalneeds);
        softAssert.assertAll();
    }

//    @Test (priority = 5)
//    public void partialUpdateTest(){
//        CreateBookingRequest requestPayload = new CreateBookingRequest();
//        requestPayload.setFirstname(DataGenerator.getFirstName());
//        requestPayload.setLastname(DataGenerator.getLastName());
//
//        response = bookingService.partialUpdateBooking(token, bookingId, requestPayload);
//        Assert.assertEquals(response.getStatusCode(),StatusCode.OK);
//
//        pojo.updatingResponse.CreateBookingRequest responsePayload = response.as(pojo.updatingResponse.CreateBookingRequest.class);
//        softAssert.assertEquals(responsePayload.firstname, requestPayload.firstname);
//        softAssert.assertEquals(responsePayload.lastname, requestPayload.lastname);
//        softAssert.assertEquals(responsePayload.totalprice, requestPayload.totalprice);
//        softAssert.assertEquals(responsePayload.depositpaid, requestPayload.depositpaid);
//        softAssert.assertEquals(responsePayload.bookingdates.checkin, requestPayload.bookingdates.checkin);
//        softAssert.assertEquals(responsePayload.bookingdates.checkout, requestPayload.bookingdates.checkout);
//        softAssert.assertEquals(responsePayload.additionalneeds, requestPayload.additionalneeds);
//        softAssert.assertAll();
//    }

    @Test (priority = 6)
    public void deleteBookingTest(){
        response = bookingService.deleteBooking(token, bookingId);
        Assert.assertEquals(response.getStatusCode(),StatusCode.CREATED);
        response = bookingService.getReport();
        Assert.assertTrue(!response.jsonPath().getList("bookingid").contains(bookingId));
    }
}
