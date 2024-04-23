package TestCases;

import Generic.ZeptoLogin;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Generic.ZeptoRoutes.ZeptoGetTicketList;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestZeptoGetAllPendingTickets extends ZeptoLogin {

    @Test
    public void ZeptoGetAllPendingTickets() {

        Response response = given()
                .cookies(cookies)
                .formParam("status", "P")
                .when()
                .post(ZeptoGetTicketList);

        response.then().log().all();

        //Status code validation
        int statusCode = response.statusCode();
        System.out.println("Status code" + statusCode);
        response.then().statusCode(200);

        //Response time validation

        long responseTime = response.getTime();
        System.out.println("response time" + responseTime);
        assertTrue(responseTime < 9000, "Response time exceeds the acceptable threshold of 10000 milliseconds");

        //Field validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);
        Object ForResponseParametersValidation = jsonPath.get("status");

        assertEquals(String.valueOf(ForResponseParametersValidation), "Success");
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);
        System.out.println("Now you're all set my friend");
    }
}

