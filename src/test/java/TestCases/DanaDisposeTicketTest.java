package TestCases;

import FileUtility.FileLibOne;
import Generic.DanaLogin;
import Generic.DanaRoutes;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DanaDisposeTicketTest extends DanaLogin {
    @Test
    public void disposeTicketDana() throws IOException{
        String ticketId = FileLibOne.getPropertyDataDana("ticketIdDana");

        String disposeUrl = DanaRoutes.ticketDispose + "/" + ticketId;

        String jsonBody = "{\"ticket\":{\"status\":\"CL\",\"folder_id\":2214690,\"subject\":\"test\",\"priority\":\"Low\",\"comment\":{\"public\":false,\"body\":\"Testing 23465\"},\"tags\":[],\"nui\":true,\"group_id\":\"Customer_Fund_Q\",\"assignee_id\":\"265342\",\"custom_fields\":[{\"id\":23259,\"value\":\"general_enquiry\"},{\"id\":23260,\"value\":\"application\"},{\"id\":23261,\"value\":\"abnormalcaserefund\"},{\"id\":23264,\"value\":\"6pack\"},{\"id\":23268,\"value\":\"account_frozen_reset_pin_rk_1\"},{\"id\":23337,\"value\":\"bank_allo_bank\"},{\"id\":23442,\"value\":\"1_hour\"}]}}";
        Response response = given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(jsonBody)
                .when()
                .put(disposeUrl);
        response.then().log().all();

        //status Code Validation
        int statusCode = response.statusCode();
        System.out.println("Status code => " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time =" + responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        System.out.println("Ticket has been disposed successfully with Ticket_Id : " + ticketId);

    }
}
