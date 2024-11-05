package TestCases;

import Generic.BBBaseClass;
import Generic.BigbasketRoutes;
import Generic.ZeptoLogin;
import Generic.ZeptoRoutes;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

public class BigabasketGetOrderListTest extends BBBaseClass {
    @Test
    public void getOrderList() {
        Response response = given()
                .cookies(BBBaseClass.cookies)
                .header("Content-Type", "application/json")
                .header("Accept","application/json, text/plain, */*")
                .formParam("customer_code", "49502448")
                .formParam("order_type", "BB")
                .when()
                .post("https://bigbasket.kapdesk.com/api/version3/ticket/get-orders-detail");
        response.then().log().all();

        //Status code validation
        int statusCode = response.getStatusCode();
        System.out.println("status code =" + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time =" + responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

    }
}
