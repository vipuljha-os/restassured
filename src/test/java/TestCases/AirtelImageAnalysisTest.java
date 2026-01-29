package TestCases;

import Generic.AirtelLogin;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Generic.AirtelRoutes.ImageAnalysis;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AirtelImageAnalysisTest extends AirtelLogin {
    @Test
    public void testImageAnalysis() {
        String requestBody = "{\n" +
                "\"clientID\": \"string\",\n" +
                "\"checkType\": \"invoice\",\n" +
                "\"image_url\": \"https://kapture-p-v2.storage.googleapis.com/14553/chat-attachments/1125/176408077378408e1a3zrz12/kfc_pedido_page-0001.jpg\"\n" +
                "}";

        Response response = given()
                .header("Authorization", "Bearer " + AirtelLogin.bearerToken)
                .header("accept", "application/json")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(ImageAnalysis);

        response.then().log().all();
        int statusCode = response.statusCode();
        System.out.println("Status code: " + statusCode);
        response.then().statusCode(200);

        long responseTime = response.getTime();
        System.out.println("Response time: " + responseTime);
        assertTrue(responseTime < 10000, "Response time exceeds the acceptable threshold of 10000 milliseconds");

        //Field validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);
        Object ForResponseParametersValidation = jsonPath.get("status");

        assertEquals(String.valueOf(ForResponseParametersValidation), "Success");
        System.out.println(ForResponseParametersValidation);
    }
}
