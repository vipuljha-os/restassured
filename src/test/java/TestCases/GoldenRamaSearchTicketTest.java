package TestCases;

import FileUtility.FileLibOne;
import Generic.GoldenRamaLogin;
import Generic.GoldenRamaRoutes;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GoldenRamaSearchTicketTest extends GoldenRamaLogin {
    @Test
    public void searchTicketGoldenRama() throws IOException {
        String ticketId = FileLibOne.getPropertyDataGoldenRama("ticketIdGoldenRama");

        Response response = RestAssured.given()
                .formParam("query", ticketId)
                .cookies(cookies)
                .post(GoldenRamaRoutes.GetTicketList);

        response.then().log().all();

        //status Code Validation
        int statusCode = response.statusCode();
        System.out.println("Status code => " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time => " + responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        // Parameter validation
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        Object responseParameterValidation = jsonPath.get("status");
        assertEquals(String.valueOf(responseParameterValidation), "Success");

        Object ticketSearchValidation = jsonPath.get("response.tickets[0].ticketId");
        assertEquals(String.valueOf(ticketSearchValidation), ticketId);

        System.out.println("-------------------------------");
        System.out.println("Status => " + responseParameterValidation);
        System.out.println("Ticket Search successfully Completed for Ticket_Id : " + ticketId);
    }

}
