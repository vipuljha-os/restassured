package TestCases;

import Generic.MeeshoSXLogin;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Generic.Routes.advertisement;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class Config63Test extends MeeshoSXLogin {
    @Test
    public void config63(){
        hardWait();
        String requestBody="{\n" +
                "    \"customerId\": 1,\n" +
                "    \"email\": \"choudharynirma2017@gmail.com\",\n" +
                "    \"phone\": \"9166662669\",\n" +
                "    \"otherDetail\": {\n" +
                "        \"name\": \"The is meesho tech testing account\",\n" +
                "        \"address\": \"Bangalore 1234 Boom bam  tttttt Sadfbgnh\",\n" +
                "        \"landmark\": \"Bangalore 1 Boom bam \",\n" +
                "        \"city\": \"Bangalore 156 Boom bam \",\n" +
                "        \"state\": \"haryana\",\n" +
                "        \"country\": \"India\",\n" +
                "        \"pin\": \"560103\",\n" +
                "        \"district\": \"#DISTRICT#\"\n" +
                "    },\n" +
                "    \"configId\": 63\n" +
                "}";
        Response response=given()
                .accept("application/json, text/plain, */*")
                .header("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8")
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .when()
                .body(requestBody)
                .post(advertisement);
        //to validate the response time
        int statusCode= response.statusCode();
        System.out.println("status code"+ statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time =" + responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Parameter validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);
        Object ForResponseParametersValidation = jsonPath.get("success");
        assertThat(String.valueOf(ForResponseParametersValidation), equalTo("true"));
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);
    }
}
