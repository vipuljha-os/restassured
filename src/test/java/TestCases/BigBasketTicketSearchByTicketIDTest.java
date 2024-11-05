package TestCases;

import Generic.BBBaseClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Generic.BigbasketRoutes.BigBasketGetTicketList;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BigBasketTicketSearchByTicketIDTest extends BBBaseClass {

    @Test
    public void getTicketSearchByTicketID() {

        Response response = given()
                .cookies(BBBaseClass.cookies)
                .header("Content-Type", "application/json") // Add this if required
                // Apply cookies here
                .formParam("status", "P")
                .formParam("type", "2")
                .formParams("query","8861872771")
                .when()
                .post(BigBasketGetTicketList);
        System.out.println("Cookies after login: " + cookies);
        response.then().log().all();
        // Status code validation
        int statusCode = response.statusCode();
        System.out.println("Status code: " + statusCode);
        assertEquals(statusCode, 200);

        // Response time validation
        long responseTime = response.getTime();
        System.out.println("Response time: " + responseTime);
        assertTrue(responseTime < 9000, "Response time exceeds the acceptable threshold of 9000 milliseconds");

        // Field validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);
        String status = jsonPath.getString("status");

        assertEquals(status, "Success", "Status field validation failed");
        System.out.println("******************************");
        System.out.println("Status: " + status);
        System.out.println("Now you're all set my friend");
    }
}
