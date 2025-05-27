package TestCases;

import FileUtility.FileLibOne;
import Generic.DanaLogin;
import Generic.DanaRoutes;
import Generic.GoldenRamaRoutes;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DanaAssignToTicketTest extends DanaLogin {
    @Test
    public void assignToTicketDana() throws IOException{
        String ticketId = FileLibOne.getPropertyDataDana("ticketIdDana");
        String taskId = FileLibOne.getPropertyDataDana("taskIdDana");

        Response response = given()
                .cookies(cookies)
                .formParam("task_id", taskId)
                .formParam("ticket_id",ticketId)
                .formParam("assign_to","265342#Test 1")
                .formParam("task_detail","Test")
                .when()
                .post(DanaRoutes.assignToTicket);

        response.then().log().all();

        //validate status code
        int statusCode = response.getStatusCode();
        System.out.println("Status Code => "+ statusCode);
        response.then().statusCode(200);

        //Response Time validation
        long responseTime = response.getTime();
        System.out.println("Response Time => " + responseTime);
        assertTrue(responseTime < 3000 , "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Param Validation
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        Object responseParamValidation = jsonPath.get("status");
        assertEquals(String.valueOf(responseParamValidation), "Success");
        System.out.println("-------------------------------");
        System.out.println("Status ==> " + responseParamValidation);
        System.out.println("Ticket with ticketId => "+ticketId+" has been successfully assigned to Dana Care");

    }
}
