package TestCases;

import Generic.MeeshoSXLogin;
import Generic.ZeptoLogin;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static Generic.ZeptoRoutes.ZeptoGetTicketList;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ZeptoGetAllPendingTickets extends ZeptoLogin {

    @Test
    public void getAllPendingTickets() {

        Response response = given()
                .cookies(cookies)
                .formParam("status", "P")
                .formParam("type","2")
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

