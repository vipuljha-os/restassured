package TestCases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CruiseBookingStep2 {
    private static final String URL = "https://bahamas.kapturecrm.com/itinerary-wise-sailing-details";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 18000;

    //    @Test
//    public static void main(String[] arg) {
//        verifyCruiseBookingStep2();
//
//    }
    @Test
    public void testCruiseBookingStep2() {
        // Call the helper method to execute the API logic
        JSONObject result = verifyCruiseBookingStep2();
    }

    public static JSONObject verifyCruiseBookingStep2() {
        Response response = null;
        try {
            CruiseBookingStep1 cruiseBookingStep1 = new CruiseBookingStep1();
            // Get the response of Step 1 (assuming the response object is stored in CruiseBookingStep1.responseOfStep1)
            JSONObject response1 = cruiseBookingStep1.verifyCruiseBookingStep1Test();
            System.out.println("***********");
            // Get the available itineraries from Step 1 response
            // Get the itinerary name and last sailing start date from Step 1 response
            String itineraryName = (String) response1.get("itinerary_title");
            System.out.println("************" + itineraryName);
            String lastSailingDate = (String) response1.get("last_sailing_start_date");
            System.out.println("************" + lastSailingDate);

            if (itineraryName == null || lastSailingDate == null) {
                throw new NullPointerException("Itinerary name or last sailing date is missing in the selected itinerary");
            }

            // Extract year and month from the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            Date date = dateFormat.parse(lastSailingDate);
            SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy/MM");
            String yearMonth = monthFormat.format(date);

            JSONObject payload = new JSONObject();
            payload.put("itinerary_name", itineraryName);
            payload.put("year_month", yearMonth);

            // Output the request body
            System.out.println("Request Body for Step 2:\n" + payload.toString());

            // API Request for Step 2
            response = given()
                    .header("Content-Type", CONTENT_TYPE)
                    .header("Authorization", AUTHORIZATION_HEADER)
                    .body(payload.toString())
                    .when()
                    .post(URL);

            // Logging the Response
            response.then().log().all();
            System.out.println("**************** Response of step 2 ********************");
            System.out.println(response.getBody().toString());

            // Validating the Response
            validateStatusCode(response);
            validateResponseTime(response);
            validateResponseBody(response);
        } catch (NullPointerException e) {
            // Log the error details to help debug
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String responseBody = response.getBody().asString();

        JSONParser parser = new JSONParser();
        JSONObject res = null;
        try {
            res = (JSONObject) parser.parse(responseBody);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray arr = (JSONArray) res.get("sailings");
        if (arr != null) {
            int size = arr.size();
            Random random = new Random();
            int randomNumber = random.nextInt(size);
            JSONObject obj = (JSONObject) arr.get(randomNumber);
            return obj;
        }
        return null;
    }

    // Validate the status code
    private static void validateStatusCode(Response response) {
        int statusCode = response.statusCode();
        System.out.println("Status Code: " + statusCode);
        response.then().statusCode(200);
    }

    // Validate the response time
    private static void validateResponseTime(Response response) {
        long responseTime = response.getTime();
        System.out.println("Response Time: " + responseTime + "ms");
    }

    // Validate the response body
    private static void validateResponseBody(Response response) {
        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);

        // Example of parameter validation using JsonPath
        JsonPath jsonPath = new JsonPath(responseBody);
        Object ForResponseParametersValidation_Status = jsonPath.get("status");
        assertThat(ForResponseParametersValidation_Status, equalTo("success"));

        // Additional validation based on your specific response structure
        // Extract the 'session_id' field from the JSON response
        Object sessionId = jsonPath.get("session_id");

// Check if the 'session_id' field is present and has a valid value
        if (sessionId != null && !sessionId.toString().trim().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'session_id' has a valid value: " + sessionId);

            // Check if the value is numeric
            if (StringUtils.isNumeric(sessionId.toString())) {
                double sessionIdValue = Double.parseDouble(sessionId.toString());

                // Check if the value is 0 or negative
                if (sessionIdValue <= 0) {
                    System.out.println("Field 'session_id' has a 0 or negative value: " + sessionIdValue);
                    assertThat("Field 'session_id' should not be 0 or negative!", sessionIdValue, greaterThan(0.0));
                } else {
                    // Value is valid (not null, not empty, and positive)
                    System.out.println("Field 'session_id' has a valid positive value: " + sessionIdValue);
                }
            } else {
                // Value is not numeric
                System.out.println("Field 'session_id' is not a numeric value: " + sessionId);
                assertThat("Field 'session_id' should be a numeric value!", false);
            }
        } else {
            // Value is null or empty
            System.out.println("Field 'session_id' is null or empty. Failing test case!");
            assertThat("Field 'session_id' is null or empty!", sessionId, notNullValue());
            assertThat("Field 'session_id' should not be empty!", sessionId.toString(), not(isEmptyString()));
        }
    }
}
