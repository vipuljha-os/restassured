package TestCases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.isEmptyString;
import static org.testng.Assert.assertTrue;

public class CruiseBookingStep7 {
    private static final String URL = "https://bahamas.kapturecrm.com/confirm-cruise-booking-guest-booking";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 18000;

    @Test
    public void VerifyCruiseBookingStep7() {
        // Request body
        String requestBody = "{\n" +
                "    \"sailing_date\": \"07/18/2025\",\n" +
                "    \"sailing_type\": \"2_day\",\n" +
                "    \"cruise_id\": \"61\",\n" +
                "    \"category_id\": \"183\",\n" +
                "    \"guest_details\": [\n" +
                "        {\n" +
                "            \"phone\": 9876543446,\n" +
                "            \"dob\": \"11/01/1990\",\n" +
                "            \"last_name\": \"Albert\",\n" +
                "            \"room_id\": \"6094\",\n" +
                "            \"title\": \"Mr.\",\n" +
                "            \"city\": \"NYC\",\n" +
                "            \"country\": \"USA\",\n" +
                "            \"nationality\": \"USAA\",\n" +
                "            \"state\": \"Florida\",\n" +
                "            \"passport_no\": \"A298765\",\n" +
                "            \"passport_expiry_date\": \"04/02/2030\",\n" +
                "            \"first_name\": \"Qwerty\",\n" +
                "            \"address\": \"1st Cross dummy details, 37/a\",\n" +
                "            \"email\": \"yash.tembhare@kapturecrm.com\",\n" +
                "            \"room_no\": \"4037\",\n" +
                "            \"gender\": \"Male\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"phone\": 9876543446,\n" +
                "            \"dob\": \"11/01/1990\",\n" +
                "            \"last_name\": \"Albert\",\n" +
                "            \"room_id\": \"6094\",\n" +
                "            \"title\": \"Mr.\",\n" +
                "            \"city\": \"NYC\",\n" +
                "            \"country\": \"USA\",\n" +
                "            \"nationality\": \"USAA\",\n" +
                "            \"state\": \"Florida\",\n" +
                "            \"passport_no\": \"A298765\",\n" +
                "            \"passport_expiry_date\": \"04/02/2030\",\n" +
                "            \"first_name\": \"Pinto\",\n" +
                "            \"address\": \"1st Cross dummy details, 37/a\",\n" +
                "            \"email\": \"yash.tembhare@kapturecrm.com\",\n" +
                "            \"room_no\": \"4037\",\n" +
                "            \"gender\": \"Male\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"total_room\": 1\n" +
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
        //validateResponseBody(response);
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

        // Validate 'payment_link' field
        Object paymentLink = jsonPath.get("payment_link");

        if (paymentLink != null && !paymentLink.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'paymentLink' has a valid value: " + paymentLink);
            assertThat("Field 'paymentLink' should have a valid value!",
                    paymentLink.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'paymentLink' is null or empty. Failing test case!");
            assertThat("Field 'paymentLink' is null or empty!", paymentLink, notNullValue());
            assertThat("Field 'paymentLink' should not be empty!", paymentLink.toString(), not(isEmptyString()));
        }

        // Validate 'bookingid' field
        Object bookingId = jsonPath.get("booking_id");

        if (bookingId != null && !bookingId.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'bookingId' has a valid value: " + bookingId);
            assertThat("Field 'bookingId' should have a valid value!",
                    bookingId.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'bookingId' is null or empty. Failing test case!");
            assertThat("Field 'bookingId' is null or empty!", bookingId, notNullValue());
            assertThat("Field 'bookingId' should not be empty!", bookingId.toString(), not(isEmptyString()));
        }
    }
}
