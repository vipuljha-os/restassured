package TestCases;

import FileUtility.FileeLib;
import Generic.ZeptoLogin;
import Generic.ZeptoRoutes;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonTypeInfo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static Generic.Routes.ticketDispose;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ZeptoTicketDispose extends ZeptoLogin {
    @Test
    public void disposeTicket() throws IOException {

        String ticketId = FileeLib.getPropertyData("ticketId");
        String taskId = FileeLib.getPropertyData("taskId");

        Response response = RestAssured.given()
                .formParam("task_id",taskId)
                .formParam("ticket_id", ticketId)
                .formParam("sub_status","CO")
                .cookies(cookies)
                .post(ZeptoRoutes.ticketDispose);

        response.then().log().all();

        int statusCode = response.statusCode();
        System.out.println("status code " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Code ="+ responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        // Parameter validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);

        Object ForResponseParametersValidation = jsonPath.get("status");
        assertEquals(String.valueOf(ForResponseParametersValidation), "Success");
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);
        System.out.println("Ticket has been disposed successfully with Ticket_Id : "+ ticketId);


    }
}
