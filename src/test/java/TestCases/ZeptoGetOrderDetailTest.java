package TestCases;

import Generic.ZeptoLogin;
import Generic.ZeptoRoutes;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ZeptoGetOrderDetailTest extends ZeptoLogin {
    @Test
    public void getOrderDetail(){
        String jsonBody = "{\"customerId\":\"\",\"email\":\"\",\"phone\":\"\",\"shipmentId\":\"\",\"ticketId\":\"723110165341\",\"taskId\":591100846,\"orderId\":\"193DBBKNH86124\",\"otherDetail\":{\"userId\":\"101b3bbd-4213-4d8d-81c4-c7bdf25c66db\"}}";
        Response response = given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(jsonBody)
                .when()
                .post(ZeptoRoutes.GetOrderDetail);
                 response.then().log().all();

        //Status code validation
        int statusCode = response.getStatusCode();
        System.out.println("status code =" + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Code ="+ responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

    }

}
