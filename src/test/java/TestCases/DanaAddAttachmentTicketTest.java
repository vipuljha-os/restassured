package TestCases;

import FileUtility.FileLibOne;
import Generic.DanaLogin;
import Generic.DanaRoutes;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DanaAddAttachmentTicketTest extends DanaLogin {
    @Test
    public void addAttachmentDana() throws IOException{
        String taskId = FileLibOne.getPropertyDataDana("taskIdDana");
        String ticketId = FileLibOne.getPropertyDataDana("ticketIdDana");

        Response response = RestAssured.given()
                .redirects().follow(false)
                .multiPart("attach_1", new File("/opt/atlassian/pipelines/agent/build/src/test/java/FileUtility/attach.jpeg"))
                .formParam("task_id",taskId)
                .formParam("response_type","json")
                .formParam("total_attachment","1")
                .cookies(cookies)
                .post(DanaRoutes.addAttachment);

        response.then().log().all();
        System.out.println("Redirect Location: " + response.getHeader("Location"));

        //status Code Validation
        int statusCode = response.statusCode();
        System.out.println("Status code => " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time => " + responseTime);
        assertTrue(responseTime < 5000, "Response time exceeds the acceptable threshold of 5000 milliseconds");

        // Parameter validation
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        Object responseParameterValidation = jsonPath.get("status");
        assertEquals(String.valueOf(responseParameterValidation), "success");

        System.out.println("-------------------------------");
        System.out.println("Status => " + responseParameterValidation);
        System.out.println("Attachment added successfully for Ticket_Id : " + ticketId);
    }
}
