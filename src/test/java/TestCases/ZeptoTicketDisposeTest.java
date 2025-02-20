package TestCases;

import FileUtility.FileLibOne;
import Generic.ZeptoLogin;
import Generic.ZeptoRoutes;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ZeptoTicketDisposeTest extends ZeptoLogin {

    @Test
    public void disposeTicket() throws IOException {
        try {
            Thread.sleep(1000); // Add a wait time of 300 milliseconds after executing login method
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String ticketId = FileLibOne.getPropertyData("ticketId");
        String taskId = FileLibOne.getPropertyData("taskId");

        Response response = RestAssured.given()
                .formParam("task_id", taskId)
                .formParam("ticket_id", ticketId)
                .formParam("sub_status", "CO")
                .cookies(cookies)
                .post(ZeptoRoutes.ticketDispose);

        response.then().log().all();

        int statusCode = response.statusCode();
        System.out.println("status code " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time =" + responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        // Parameter validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);

        Object ForResponseParametersValidation = jsonPath.get("status");
        assertEquals(String.valueOf(ForResponseParametersValidation), "Success");
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);
        System.out.println("Ticket has been disposed successfully with Ticket_Id : " + ticketId);
    }
}
