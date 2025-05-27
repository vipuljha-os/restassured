package TestCases;

import FileUtility.FileLibOne;
import Generic.DanaLogin;
import Generic.DanaRoutes;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DanaSearchTicketTest extends DanaLogin {
    @Test
    public void searchTicketDana() throws IOException {

        String ticketId = FileLibOne.getPropertyDataDana("ticketIdDana");

        Response response = given()
                .cookies(cookies)
                .formParam("query",ticketId)
                .when()
                .post(DanaRoutes.GetTicketList);

        response.then().log().all();

        //status code validation
        int statusCode = response.getStatusCode();
        System.out.println("Status Code => " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response time => " + responseTime);
        assertTrue(responseTime < 3000 ,"Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Response Param validation
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        Object responseParamValidation = jsonPath.get("status");
        assertEquals(String.valueOf(responseParamValidation),"Success");

        Object ticketSearchValidation = jsonPath.get("response.tickets[0].ticketId");
        assertEquals(String.valueOf(ticketSearchValidation),ticketId);

        System.out.println("---------------------------------");
        System.out.println("Status => " + responseParamValidation);
        System.out.println("Ticket Search successfully Completed for Ticket Id : " + ticketId);
    }
}
