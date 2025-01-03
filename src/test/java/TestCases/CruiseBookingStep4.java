package TestCases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class CruiseBookingStep4 {
    private static final String URL = "https://bahamas.kapturecrm.com/get-cruise-category-availability";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 18000;

    @Test
    public void VerifyCruiseBookingStep4() {
        // Request body
        String requestBody = "{\n" +
                "    \"sailing_type\": \"1_way_onward\",\n" +
                "    \"cruise_id\": \"61\",\n" +
                "    \"sailing_date\": \"07/18/2025\"\n" +
                "}";

        // API Request
        Response response = given()
                .header("Content-Type", CONTENT_TYPE)
                .header("Authorization", AUTHORIZATION_HEADER)
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
        System.out.println("******************************");

        // Validate 'sessionId' field
        Object sessionId = jsonPath.get("session_id");

        if (sessionId != null && !sessionId.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'sessionId' has a valid value: " + sessionId);
            assertThat("Field 'sessionId' should have a valid value!",
                    sessionId.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'sessionId' is null or empty. Failing test case!");
            assertThat("Field 'sessionId' is null or empty!", sessionId, notNullValue());
            assertThat("Field 'sessionId' should not be empty!", sessionId.toString(), not(isEmptyString()));
        }

        // Validate '@.1_way_onward.sailing_type' field
        Object wayOnwardSailingType = jsonPath.get("1_way_onward.sailing_type");

        if (wayOnwardSailingType != null && !wayOnwardSailingType.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'wayOnwardSailingType' has a valid value: " + sessionId);
            assertThat("Field 'wayOnwardSailingType' should have a valid value!",
                    wayOnwardSailingType.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'wayOnwardSailingType' is null or empty. Failing test case!");
            assertThat("Field 'wayOnwardSailingType' is null or empty!", wayOnwardSailingType, notNullValue());
            assertThat("Field 'wayOnwardSailingType' should not be empty!", wayOnwardSailingType.toString(), not(isEmptyString()));
        }
        // Validate '@.1_way_onward.availability' field
        Object Onewayonwardavailability = jsonPath.get("1_way_onward.availability");

        if (Onewayonwardavailability != null && !Onewayonwardavailability.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'Onewayonwardavailability' has a valid value: " + Onewayonwardavailability);
            assertThat("Field 'Onewayonwardavailability' should have a valid value!",
                    Onewayonwardavailability.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'Onewayonwardavailability' is null or empty. Failing test case!");
            assertThat("Field 'Onewayonwardavailability' is null or empty!", Onewayonwardavailability, notNullValue());
            assertThat("Field 'Onewayonwardavailability' should not be empty!", Onewayonwardavailability.toString(), not(isEmptyString()));
        }

    }
}