package TestCases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class CruiseSailingPricingDetailsTest {
    private static final String URL = "https://bahamas.kapturecrm.com/cruise-sailing-pricing-details";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 2000;

    @Test
    public void getCruiseSailingPricingDetails() {
        // To access Request Body
        String requestBody = null;
        try {
            // Specify the path to the request body file
            String filePath = "C:\\kapture Restassured\\backendtests\\src\\test\\java\\FileUtility\\RequestBody.json";

            // Read the file content into a String
            requestBody = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // API Request
        Response response = given()
                .header("Authorization", AUTHORIZATION_HEADER)
                .header("Content-Type", CONTENT_TYPE)
                .body(requestBody)
                .when()
                .post(URL);

        // Logging the Response
        response.then().log().all();

        // Validating the Response
        validateStatusCode(response);
        validateResponseTime(response);
        validateResponseBody(response);
    }

    // Validate the status code
    private void validateStatusCode(Response response) {
        int statusCode = response.statusCode();
        System.out.println("Status Code: " + statusCode);
        response.then().statusCode(200);
    }

    // Validate the response time
    private void validateResponseTime(Response response) {
        long responseTime = response.getTime();
        System.out.println("Response Time: " + responseTime + "ms");
        assertTrue(responseTime < MAX_RESPONSE_TIME,
                "Response time exceeds the acceptable threshold of " + MAX_RESPONSE_TIME + " milliseconds");
    }

    // Validate the response body
    private void validateResponseBody(Response response) {
        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);
        //Parameter validation
        JsonPath jsonPath = new JsonPath(responseBody);
        Object ForResponseParametersValidation_Status = jsonPath.get("status");
        assertThat(ForResponseParametersValidation_Status, equalTo("success"));
        Object ForResponseParametersValidation_Message = jsonPath.get("message");
        assertThat(ForResponseParametersValidation_Message, equalTo("Sailings Details"));
        System.out.println("******************************");

        // Validate 'room_price' field
        Object roomPrice = jsonPath.get("sailings[0].availablity[0].room_price");

        if (roomPrice != null && !roomPrice.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'room_price' has a valid value: " + roomPrice);
            assertThat("Field 'room_price' should have a valid value!",
                    roomPrice.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'room_price' is null or empty. Failing test case!");
            assertThat("Field 'room_price' is null or empty!", roomPrice, notNullValue());
            assertThat("Field 'room_price' should not be empty!", roomPrice.toString(), not(isEmptyString()));
        }
        // Validate 'room_price_Offer' field
        Object roomPriceOffer = jsonPath.get("sailings[0].availablity[0].room_price_offer");

        if (roomPriceOffer != null && !roomPriceOffer.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'room_price' has a valid value: " + roomPriceOffer);
            assertThat("Field 'room_price' should have a valid value!",
                    roomPrice.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'room_price' is null or empty. Failing test case!");
            assertThat("Field 'room_price' is null or empty!", roomPriceOffer, notNullValue());
            assertThat("Field 'room_price' should not be empty!", roomPriceOffer.toString(), not(isEmptyString()));
        }

    }
}
