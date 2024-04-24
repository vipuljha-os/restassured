package TestCases;

import Generic.MeeshoSXLogin;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static Generic.Routes.addNote;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

public class addNoteTest extends MeeshoSXLogin {
    @Test
    public void addNote(){
        hardWait();
        Response response = RestAssured.given()
                .cookies(cookies)
                .formParam("task_id", "528961224")
                .formParam("ticket_id", "8713333244222")
                .formParam("assigned_to", "0")
                .formParam("creator", "0")
                .formParam("status", "C")
                .formParam("customer_id", "129115675")
                .formParam("note", "<p>TEST for RestAssured</p>")
                .when()
                .get(addNote);

        //Status code validation
        int statusCode = response.getStatusCode();
        System.out.println("status code " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Code ="+ responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Parameter validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);
        Object ForResponseParametersValidation = jsonPath.get("response.PAGE_NAME");
        assertThat(String.valueOf(ForResponseParametersValidation), Matchers.equalTo("null"));
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);
    }
}
