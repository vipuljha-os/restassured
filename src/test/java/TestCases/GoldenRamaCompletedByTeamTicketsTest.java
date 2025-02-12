package TestCases;

import Generic.GoldenRamaLogin;
import Generic.GoldenRamaRoutes;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GoldenRamaCompletedByTeamTicketsTest extends GoldenRamaLogin {
    @Test
    public void getCompletedByTeamTickets(){
        Response response = given()
                .cookies(cookies)
                .formParam("type","9")
                .formParam("status","C")
                .when()
                .post(GoldenRamaRoutes.GetTicketList);

        response.then().log().all();

        //Status code validation
        int statusCode = response.getStatusCode();
        System.out.println("Status Code => " + statusCode);
        response.then().statusCode(200);

        //Response Time Validation
        long responseTime = response.getTime();
        System.out.println("Response Time => " + responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Response Param Validation
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        Object responseParamValidation = jsonPath.get("status");
        assertEquals(String.valueOf(responseParamValidation),"Success");
        System.out.println("---------------------------------");
        System.out.println("Status => " + responseParamValidation);
    }
}
