package TestCases;

import Generic.GoldenRamaLogin;
import Generic.GoldenRamaRoutes;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GoldenRamaGetAssignedToMeTicketsTest extends GoldenRamaLogin {
    @Test
    public void getAssignedToMeTickets(){
        Response response = given()
                .cookies(cookies)
                .formParam("type","5")
                .when()
                .post(GoldenRamaRoutes.GetTicketList);

        response.then().log().all();

        //status code validation
        int statusCode = response.getStatusCode();
        System.out.println("Status Code => " + statusCode);
        response.then().statusCode(200);

        //Response Time validation
        long responseTime = response.getTime();
        System.out.println("Response Time => " + responseTime);
        assertTrue(responseTime < 3000 , "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Response param validation
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        Object responseParamValidation = jsonPath.get("status");
        assertEquals(String.valueOf(responseParamValidation),"Success");
        System.out.println("---------------------------------");
        System.out.println("Status => " + responseParamValidation);
    }

}
