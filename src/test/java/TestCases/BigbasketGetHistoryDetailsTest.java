package TestCases;

import Generic.BBBaseClass;
import Generic.BigbasketRoutes;
import Generic.ZeptoLogin;
import Generic.ZeptoRoutes;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BigbasketGetHistoryDetailsTest extends BBBaseClass {
    @Test
    public void getHistoryDetails() {
        Response response = given()
                .cookies(BBBaseClass.cookies)
                .formParam("id", "575082698")
                .formParam("ticket_id", "6729600249368")
                .formParam("data_type", "history")
                .when()
                .post(BigbasketRoutes.GetTicketDetail);
        response.then().log().all();

        //Status code validation
        int statusCode = response.getStatusCode();
        System.out.println("status code =" + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time =" + responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);

        Object ForResponseParametersValidation = jsonPath.get("status");
        assertEquals(String.valueOf(ForResponseParametersValidation), "Success");
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);
    }
}
