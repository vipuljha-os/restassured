package TestCases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class CruiseBookingStep777 {
    private static final String URL = "https://bahamas.kapturecrm.com/confirm-cruise-booking-guest-booking";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 18000;

    @Test
    public void testCruiseBookingStep7() {
        // Call the helper method to execute the API logic
        Object result = VerifyCruiseBookingStep7();
    }
    public Object VerifyCruiseBookingStep7() {
        CruiseBookingStep577 cruiseBookingStep5 = new CruiseBookingStep577();
        // Get the response of Step 1 (assuming the response object is stored in CruiseBookingStep1.responseOfStep1)
        JSONObject response1 = cruiseBookingStep5.VerifyCruiseBookingStep5();
        System.out.println("***********");


        // Get the room_id name
        String sailingDate = (String) response1.get("sailing_date");
        System.out.println("************" + sailingDate);

        // Get the room_id name
        String sailing_type = (String) response1.get("sailing_type");
        System.out.println("************" + sailing_type);

        JSONArray arr = (JSONArray) response1.get("room_details");
        String room_id = null;
        String categoryId = null;
        if (arr != null) {
            int size = arr.size();
            Random random = new Random();
            int randomNumber = random.nextInt(size);
            JSONObject obj = (JSONObject) arr.get(randomNumber);
            System.out.println(obj);

            // Get the room_id name
            room_id = (String) obj.get("room_id");
            System.out.println("************" + room_id);

            // Get the room_id name
            categoryId = (String) obj.get("category_id");
            System.out.println("************" + categoryId);

        }

        // Request body
        String requestBody = "{\n" + "    \"sailing_date\": \"" + sailingDate + "\",\n" + "    \"sailing_type\": \"" + sailing_type + "\",\n" + "    \"cruise_id\": \"77\",\n" + "    \"category_id\": \"" + categoryId + "\",\n" + "    \"guest_details\": [\n" + "        {\n" + "            \"phone\": 9876543446,\n" + "            \"dob\": \"11/01/1990\",\n" + "            \"last_name\": \"Albert\",\n" + "            \"room_id\": \"" + room_id + "\",\n" + "            \"title\": \"Mr.\",\n" + "            \"city\": \"NYC\",\n" + "            \"country\": \"USA\",\n" + "            \"nationality\": \"USAA\",\n" + "            \"state\": \"Florida\",\n" + "            \"passport_no\": \"A298765\",\n" + "            \"passport_expiry_date\": \"04/02/2030\",\n" + "            \"first_name\": \"Qwerty\",\n" + "            \"address\": \"1st Cross dummy details, 37/a\",\n" + "            \"email\": \"yash.tembhare@kapturecrm.com\",\n" + "            \"room_no\": \"4037\",\n" + "            \"gender\": \"Male\"\n" + "        },\n" + "        {\n" + "            \"phone\": 9876543446,\n" + "            \"dob\": \"11/01/1990\",\n" + "            \"last_name\": \"Albert\",\n" + "            \"room_id\": \"" + room_id + "\",\n" + "            \"title\": \"Mr.\",\n" + "            \"city\": \"NYC\",\n" + "            \"country\": \"USA\",\n" + "            \"nationality\": \"USAA\",\n" + "            \"state\": \"Florida\",\n" + "            \"passport_no\": \"A298765\",\n" + "            \"passport_expiry_date\": \"04/02/2030\",\n" + "            \"first_name\": \"Pinto\",\n" + "            \"address\": \"1st Cross dummy details, 37/a\",\n" + "            \"email\": \"yash.tembhare@kapturecrm.com\",\n" + "            \"room_no\": \"4037\",\n" + "            \"gender\": \"Male\"\n" + "        }\n" + "    ],\n" + "    \"total_room\": 1\n" + "}";

        System.out.println(requestBody);
        // API Request
        Response response = given().header("Content-Type", CONTENT_TYPE).header("Authorization", AUTHORIZATION_HEADER).body(requestBody).when().post(URL);

        // Logging the Response
        response.then().log().all();
        String responseBody = response.getBody().asString();
        System.out.println("**************** response of step 7 ****************");
        System.out.println(requestBody);
        // Validating the Response
        validateStatusCode(response);
        validateResponseTime(response);
        validateResponseBody(response);
        System.out.println(response);
        return null;
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
        assertTrue(responseTime < MAX_RESPONSE_TIME, "Response time exceeds the acceptable threshold of " + MAX_RESPONSE_TIME + " milliseconds");
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
            assertThat("Field 'paymentLink' should have a valid value!", paymentLink.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'paymentLink' is null or empty. Failing test case!");
            assertThat("Field 'paymentLink' is null or empty!", paymentLink, notNullValue());
            assertThat("Field 'paymentLink' should not be empty!", paymentLink.toString(), not(isEmptyString()));
        }

        // Extract the 'booking_id' field from the JSON response
        Object bookingId = jsonPath.get("booking_id");

// Check if the 'booking_id' field is present and has a valid value
        if (bookingId != null && !bookingId.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'bookingId' has a valid value: " + bookingId);

            // Check if the value is numeric
            if (StringUtils.isNumeric(bookingId.toString())) {
                long bookingIdValue = Long.parseLong(bookingId.toString());

                // Check if the value is 0 or negative
                if (bookingIdValue <= 0) {
                    System.out.println("Field 'bookingId' has a 0 or negative value: " + bookingIdValue);
                    assertThat("Field 'bookingId' should not be 0 or negative!", bookingIdValue, greaterThan(0L));
                } else {
                    // Value is valid (not null, not empty, and positive)
                    System.out.println("Field 'bookingId' has a valid positive value: " + bookingIdValue);
                }
            } else {
                // Value is not numeric
                System.out.println("Field 'bookingId' is not a numeric value: " + bookingId);
                assertThat("Field 'bookingId' should be a numeric value!", false);
            }
        } else {
            // Value is null or empty
            System.out.println("Field 'bookingId' is null or empty. Failing test case!");
            assertThat("Field 'bookingId' is null or empty!", bookingId, notNullValue());
            assertThat("Field 'bookingId' should not be empty!", bookingId.toString(), not(isEmptyString()));
        }
    }
}
