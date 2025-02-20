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

public class DanaGetTicketDetailsTest extends DanaLogin {
    @Test
    public void getTicketDetailDana() throws IOException{
        String ticketId = FileLibOne.getPropertyDataDana("ticketIdDana");
        Response response = given()
                .queryParam("ticket_id",ticketId)
                .queryParam("data_type","ticket")
                .cookies(cookies)
                .when()
                .get(DanaRoutes.GetTicketDetail);

        response.then().log().all();

        //Status code validation
        int statusCode = response.getStatusCode();
        System.out.println("Status Code => " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time => " + responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Response Param Validation
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        Object responseParamValidation = jsonPath.get("status");
        assertEquals(String.valueOf(responseParamValidation),"Success");

        Object ticketIdValidation = jsonPath.get("response.ticket.ticketId");
        assertEquals(String.valueOf(ticketIdValidation),ticketId);

        System.out.println("------------------------------");
        System.out.println("Status => " + responseParamValidation);
        System.out.println("Successfully Validated Ticket details for Ticket Id => " + ticketId);
    }
}
