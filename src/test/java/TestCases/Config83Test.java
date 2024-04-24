package TestCases;

import Generic.MeeshoSXLogin;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Generic.Routes.advertisement;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

public class Config83Test extends MeeshoSXLogin {
    @Test
    public void config83(){
        hardWait();
        String requestBody="{\"configId\":83,\"customerId\":\"1\"}";
        Response response=given()
                .accept("application/json, text/plain, */*")
                .header("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8")
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .when()
                .body(requestBody)
                .post(advertisement);
        //to validate the response time
        int statusCode= response.statusCode();
        System.out.println("status code"+ statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time =" + responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Parameter validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);
//        Object ForResponseParametersValidation = jsonPath.get("reason");
//        assertThat(String.valueOf(ForResponseParametersValidation), equalTo("[null]"));
//        System.out.println("******************************");
//        System.out.println(ForResponseParametersValidation);
    }
}
