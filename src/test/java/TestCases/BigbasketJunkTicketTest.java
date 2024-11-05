package TestCases;

import FileUtility.FileLibOne;
import Generic.BBBaseClass;
import Generic.BigbasketRoutes;
import Generic.ZeptoLogin;
import Generic.ZeptoRoutes;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BigbasketJunkTicketTest extends BBBaseClass {

    @Test
    public void junkTicket() throws IOException {
//        String ticketId = FileLibOne.getPropertyData("ticketId");
//        String taskId = FileLibOne.getPropertyData("taskId");

        Response response = RestAssured.given()
                .formParam("task_id", "575082698")
                .formParam("ticket_id", "6729600249368")
                .formParam("remark", "test")
                .cookies(cookies)
                .post(BigbasketRoutes.JunkTicket);

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
        assertEquals(String.valueOf(ForResponseParametersValidation), "success");
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);
    }
}
