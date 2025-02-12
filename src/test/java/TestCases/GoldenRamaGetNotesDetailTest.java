package TestCases;

import FileUtility.FileLibOne;
import Generic.GoldenRamaLogin;
import Generic.GoldenRamaRoutes;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GoldenRamaGetNotesDetailTest extends GoldenRamaLogin {
    @Test
    public void getNotesDetail() throws IOException {
        String taskId = FileLibOne.getPropertyData("taskId");
        String ticketId = FileLibOne.getPropertyData("ticketId");
        String note = "This is test by Kapture";

        Response response = given()
                .formParam("id", taskId)
                .formParam("ticket_id", ticketId)
                .formParam("data_type", "NOTES")
                .cookies(cookies)
                .when()
                .post(GoldenRamaRoutes.GetTicketDetail);

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
        assertEquals(String.valueOf(responseParamValidation), "Success");

        List<String> noteDataValidation = jsonPath.getList("response.notes.detail");
        assertTrue(noteDataValidation.contains(note), "No note found with the expected detail value");

        System.out.println("------------------------------");
        System.out.println("Status => " + responseParamValidation);
        System.out.println("Successfully Added a note for Ticket id => " + noteDataValidation);

    }
}
