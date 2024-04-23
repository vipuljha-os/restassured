package TestCases;

import Generic.MeeshoSXLogin;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static Generic.Routes.GetTicketDetail;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GetTicketDetailsTest extends MeeshoSXLogin {
    @Test
    public void getTicketDetails() {
        Response response = given()
                .cookies(cookies)
                .formParam("id", "527494808")
                .formParam("data_type", "CUSTOM_COMMUNICATIONS")
                .formParam("ticket_id", "8713026394129")
                .when()
                .post(GetTicketDetail);

        //Status code validation
        int statusCode = response.getStatusCode();
        System.out.println("status code =" + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Code ="+ responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Parameter validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);
        Object ForResponseParametersValidation = jsonPath.get("response.customCommunication[0].cmId");
        // Convert actual value to string before asserting
        assertEquals("1000042", String.valueOf(ForResponseParametersValidation));
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);
    }
}
