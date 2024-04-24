package TestCases;
import Generic.MeeshoSXLogin;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Generic.Routes.GetTicketList;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GetAllPendingTicketsTest extends MeeshoSXLogin {
    @Test
    public void getAllPendingTickets() {
        hardWait();
        System.out.println(cookies.toString());

        Response response = given()
                .cookies(cookies)
                .formParam("status", "P")
                .when()
                .post(GetTicketList);

        response.then().log().all();


        //Status code validation
        int statusCode = response.statusCode();
        System.out.println("Status code" + statusCode);
        response.then().statusCode(200);

        //Response time validation

        long responseTime = response.getTime();
        System.out.println("response time" + responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

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

