package base;

import constants.PathConstant;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utility.PropertyReader;

import java.util.HashMap;
import java.util.Map;

public class BaseService {

    private static RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

    protected void buildService() {

        String env = System.getProperty("env") == null ? "qa" : System.getProperty("env");
        PropertyReader propertyReader = new PropertyReader(PathConstant.APIDETAILSFILEPATH);
        String baseUri = propertyReader.getValue("booker.baseuri." + env);

        requestSpecBuilder.setBaseUri(baseUri)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .log(LogDetail.ALL);
    }

    protected void setCookie(Map<String,String> cookie){
        requestSpecBuilder.addCookies(cookie);
    }

    protected void setPathParameter(Map<String, Object> pathParam) {
        requestSpecBuilder.addPathParams(pathParam);
    }

    protected void removePathParameter(String pathParam) {
        requestSpecBuilder.removePathParam(pathParam);
    }

    protected void setAuthentication(String token) {
        requestSpecBuilder.addHeader("Authorization", "Basic " + token);
    }

    protected void setBody(Object obj) {
        requestSpecBuilder.setBody(obj);
    }

    protected Response executeApi(String httpMethod, String endPoint) {

        RequestSpecification requestSpecification = RestAssured.given().spec(requestSpecBuilder.build());
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.log(LogDetail.ALL);
        Response response = null;

        switch (httpMethod.toUpperCase()) {
            case "GET":
                response = requestSpecification
                        .when()
                        .get(endPoint).then().spec(responseSpecBuilder.build()).extract().response();
                break;
            case "POST":
                response = requestSpecification
                        .when()
                        .post(endPoint).then().spec(responseSpecBuilder.build()).extract().response();
                break;
            case "PUT":
                response = requestSpecification
                        .when()
                        .put(endPoint).then().spec(responseSpecBuilder.build()).extract().response();
                break;
            case "PATCH":
                response = requestSpecification
                        .when()
                        .patch(endPoint).then().spec(responseSpecBuilder.build()).extract().response();
                break;
            case "DELETE":
                response = requestSpecification
                        .when()
                        .delete(endPoint).then().spec(responseSpecBuilder.build()).extract().response();
                break;
        }
        return response;
    }
}
