package TestCases;

import FileUtility.FileLibOne;
import Generic.GoldenRamaLogin;
import Generic.GoldenRamaRoutes;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GoldenRamaGetTicketHistoryTest extends GoldenRamaLogin {
    @Test
    public  void getTicketHistoryGoldenRama() throws IOException {
        String taskId = FileLibOne.getPropertyDataGoldenRama("taskIdGoldenRama");

        Response response = given()
                .queryParam("id",taskId)
                .queryParam("data_type","history")
                .cookies(cookies)
                .when()
                .get(GoldenRamaRoutes.GetTicketDetail);

        response.then().log().all();

        //Status code validation
        int statusCode = response.getStatusCode();
        System.out.println("Status Code => " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time => " + responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Response Param Validation
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        Object responseParamValidation = jsonPath.get("status");
        assertEquals(String.valueOf(responseParamValidation),"Success");

        System.out.println("------------------------------");
        System.out.println("Status => " + responseParamValidation);

    }
}
