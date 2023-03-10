package services;

import base.BaseService;
import constants.PathConstant;
import constants.StatusCode;
import io.restassured.response.Response;
import org.testng.Assert;
import pojo.TokenRequest;
import utility.PropertyReader;

import java.util.HashMap;

public class GenerateTokenService extends BaseService {

    PropertyReader propertyReader = new PropertyReader(PathConstant.APIDETAILSFILEPATH);

    public String createToken(){
        buildService();
        TokenRequest tokenRequest = TokenRequest.builder()
                .username(propertyReader.getValue("booker.username.qa"))
                .password(propertyReader.getValue("booker.password.qa"))
                .build();
        setBody(tokenRequest);
        Response res = executeApi("POST", PathConstant.CREATETOKEN);
        Assert.assertEquals(res.statusCode(), StatusCode.OK);
        String token = res.jsonPath().getString("token");
        return token;
    }

    public void removeAttributes(){
        setCookie(new HashMap<>());
        removePathParameter("bookingId");
    }
}
