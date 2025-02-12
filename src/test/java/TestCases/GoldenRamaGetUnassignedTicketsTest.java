package TestCases;

import Generic.GoldenRamaLogin;
import Generic.GoldenRamaRoutes;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GoldenRamaGetUnassignedTicketsTest extends GoldenRamaLogin {
    @Test
    public void getUnassignedTickets() {
        Response response = given()
                .cookies(cookies)
                .formParam("type", "1")
                .when()
                .post(GoldenRamaRoutes.GetTicketList);

        response.then().log().all();

        //status code validation
        int statusCode = response.getStatusCode();
        System.out.println("Status code ==> " + statusCode);
        response.then().statusCode(200);

        //response time validation
        long responseTime = response.getTime();
        System.out.println("Response time ==> " + responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //parameter validation
        String responseBody = response.getBody().asString();

        JsonPath jsonPath = new JsonPath(responseBody);
        Object ResponseParamValidation = jsonPath.get("status");
        assertEquals(String.valueOf(ResponseParamValidation), "Success");
        System.out.println("------------------------------");
        System.out.println("Status ==> " + ResponseParamValidation);
    }
}
