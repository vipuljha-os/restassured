package TestCases;

import FileUtility.FileLibOne;
import Generic.GoldenRamaLogin;
import Generic.GoldenRamaRoutes;
import Generic.ZeptoRoutes;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GoldenRamaDisposeTicketTest extends GoldenRamaLogin {

    @Test
    public void disposeTicket() throws IOException {
        String ticketId = FileLibOne.getPropertyData("ticketId");
        String taskId = FileLibOne.getPropertyData("taskId");

        Response response = RestAssured.given()
                .formParam("task_id", taskId)
                .formParam("ticket_id", ticketId)
                .formParam("sub_status", "C-NT")
                .cookies(cookies)
                .post(GoldenRamaRoutes.ticketDispose);

        response.then().log().all();

        //status Code Validation
        int statusCode = response.statusCode();
        System.out.println("Status code => " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time =" + responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        // Parameter validation
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        Object responseParameterValidation = jsonPath.get("status");
        assertEquals(String.valueOf(responseParameterValidation), "Success");
        System.out.println("-------------------------------");
        System.out.println("Status => " + responseParameterValidation);
        System.out.println("Ticket has been disposed successfully with Ticket_Id : " + ticketId);

    }
}
