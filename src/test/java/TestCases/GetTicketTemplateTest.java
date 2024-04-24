package TestCases;

import FileUtility.FileLib;
import Generic.MeeshoSXLogin;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static Generic.Routes.ticketTemplate;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GetTicketTemplateTest extends MeeshoSXLogin {
    @Test
    public void getTicketTemplates() {
        hardWait();
        // Read JSON file
        String jsonBody = null;
        try {
            jsonBody = new String(Files.readAllBytes(Paths.get("C:\\Inteleje test\\KaptureRestassured\\src\\test\\java\\FileUtility\\config65RequestBody.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String RequestBody = "{\"id\":\"\",\"type\":\"L\"}";
        Response response=null;
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(RequestBody)
                .post(ticketTemplate);
        try {
            // cookies =FileLib.getPropertyData("StoredCookies");
            HashMap map= new HashMap();
            map.put("Cookies", FileLib.getPropertyData("StoredCookies"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Status code validation
        int statusCode = response.statusCode();
        System.out.println("status code " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time ="+ responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Parameter validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);

        Object ForResponseParametersValidation = jsonPath.get("status");
        //assertEquals("null", String.valueOf(ForResponseParametersValidation));
        assertEquals(ForResponseParametersValidation, "success");
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);
    }
}
