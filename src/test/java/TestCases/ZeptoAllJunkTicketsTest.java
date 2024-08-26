package TestCases;

import Generic.ZeptoLogin;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Generic.ZeptoRoutes.ZeptoGetTicketList;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ZeptoAllJunkTicketsTest extends ZeptoLogin {
    @Test
    public void getAllJunkTickets() {
        Response response = given()
                .cookies(cookies)
                .formParam("status", "J")
                .formParam("type", "4")
                .when()
                .post(ZeptoGetTicketList);

        response.then().log().all();
        int statusCode = response.statusCode();
        System.out.println("Status code" + statusCode);
        response.then().statusCode(200);

        long responseTime = response.getTime();
        System.out.println("response time" + responseTime);
        assertTrue(responseTime < 10000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Field validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);
        Object ForResponseParametersValidation = jsonPath.get("status");

        assertEquals(String.valueOf(ForResponseParametersValidation), "Success");
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);

    }
}
