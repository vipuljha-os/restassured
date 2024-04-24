package TestCases;

import Generic.MeeshoSXLogin;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static Generic.Routes.GetTicketList;
import static org.testng.Assert.assertTrue;

public class SearchTicketByEmailTest extends MeeshoSXLogin {
    @Test
    public void search() {
        hardWait();
        Response response = RestAssured.given()
                .cookies(cookies)
                .formParam("query", "shankar.lamani@kapturecrm.com")
                .when()
                .get(GetTicketList);

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
        MatcherAssert.assertThat(ForResponseParametersValidation, Matchers.equalTo("Pending"));
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);
    }
}
