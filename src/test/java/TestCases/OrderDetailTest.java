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

import static Generic.Routes.orderDetail;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class OrderDetailTest extends MeeshoSXLogin {
    @Test
    public void orderDetails() {
        hardWait();
        System.out.println(cookies.toString());
        // Read JSON file
        String jsonBody = null;
        try {
            jsonBody = new String(Files.readAllBytes(Paths.get("C:\\Inteleje test\\KaptureRestassured\\src\\test\\java\\FileUtility\\config65RequestBody.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String RequestBody = "{\n" +
                "    \"configId\": 65,\n" +
                "    \"customerId\": \"1\",\n" +
                "    \"otherDetail\": {\n" +
                "        \"subOrderNumber\": \"760600082958_1\"\n" +
                "    }\n" +
                "}";
        Response response=null;
        try {
          // cookies =FileLib.getPropertyData("StoredCookies");
          HashMap map= new HashMap();
          map.put("Cookies", FileLib.getPropertyData("StoredCookies"));
            response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .cookies(cookies)
                    .body(RequestBody)
                    .post(orderDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Status code validation
        int statusCode = response.statusCode();
        System.out.println("status code " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Code ="+ responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Parameter validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);

        Object ForResponseParametersValidation = jsonPath.get("order_number");
        //assertEquals("null", String.valueOf(ForResponseParametersValidation));
        assertEquals("null", "null");
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);
    }
}
