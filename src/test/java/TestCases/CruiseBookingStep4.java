package TestCases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class CruiseBookingStep4 {
    private static final String URL = "https://bahamas.kapturecrm.com/get-cruise-category-availability";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 18000;

    public static JSONObject twoDayObject = null;
    @Test
    public static void main(String[] arg) {
        VerifyCruiseBookingStep4();
    }

    @Test
    public static JSONObject VerifyCruiseBookingStep4() {
        try {
            CruiseBookingStep3 cruiseBookingStep3 = new CruiseBookingStep3();
            // Get the response of Step 3
            JSONObject response1 = (JSONObject) cruiseBookingStep3.CruiseBookingStep3();
            System.out.println("***********");

            // Extract details from Step 3 response
            String sailingType = (String) response1.get("sailing_type");
            long cruiseId = (long) response1.get("cruise_id");
            String lastSailingDate = (String) response1.get("sailing_date");

            System.out.println("Sailing Type: " + sailingType);
            System.out.println("Cruise ID: " + cruiseId);
            System.out.println("Sailing Date: " + lastSailingDate);

            // Create payload for Step 4
            JSONObject payload = new JSONObject();
            payload.put("sailing_type", sailingType);
            payload.put("cruise_id", cruiseId);
            payload.put("sailing_date", lastSailingDate);

            System.out.println("Request Body for Step 4:\n" + payload.toString());

            // Make API Request
            Response response = given()
                    .header("Content-Type", CONTENT_TYPE)
                    .header("Authorization", AUTHORIZATION_HEADER)
                    .body(payload.toString())
                    .when()
                    .post(URL);

            // Log and validate response
            response.then().log().all();
            System.out.println("**************** Response of Step 4 ****************");

            validateStatusCode(response);
            validateResponseTime(response);
            // validateResponseBody(response);

            // Parse the response body
            JSONParser parser = new JSONParser();
            String responseBody = response.getBody().asString();
            System.out.println(responseBody);
            JSONObject parsedResponse = (JSONObject) parser.parse(responseBody);

            // Extract the first category ID with available rooms
            Map<String, String> details = extractCruiseBookingDetails(parsedResponse);
            String firstCategoryId = details.get("category_id");
            String sailingDate = details.get("sailing_date");
            String extractedSailingType = details.get("sailing_type");

            System.out.println("First Category ID: " + firstCategoryId);
            System.out.println("Extracted Sailing Date: " + sailingDate);
            System.out.println("Extracted Sailing Type: " + extractedSailingType);

            if (firstCategoryId != null) {
                System.out.println("Selected Category ID: " + firstCategoryId);
            } else {
                System.out.println("No category ID found with available rooms.");
            }

            return parsedResponse; // Return the parsed response if needed

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> extractCruiseBookingDetails(JSONObject response) {
        Map<String, String> bookingDetails = new HashMap<>();
        try {
            // Check for "2_day" or fallback to "1_way_onward"
            JSONObject twoDayObject = (JSONObject) response.get("2_day");
            boolean is2day = true;
            if (twoDayObject == null) {
                twoDayObject = (JSONObject) response.get("1_way_onward");
                is2day = false;
            }

            if (twoDayObject != null) {
                System.out.println("Itinerary Details: " + twoDayObject);

                // Extract sailing details
                String sailingDate = (String) twoDayObject.get("sailing_date");
                String sailingType = (String) twoDayObject.get("sailing_type");
                bookingDetails.put("sailing_date", sailingDate);
                bookingDetails.put("sailing_type", sailingType);


                // If sailing_type is "2_day", print "hello"
                if (is2day) {
                    System.out.println("test");
                } else {
                    validateResponseBody1WayOnWard(response);
                }

                // Extract category details
                JSONArray availabilityArray = (JSONArray) twoDayObject.get("availability");
                if (availabilityArray != null) {
                    for (Object obj : availabilityArray) {
                        JSONObject roomInfo = (JSONObject) obj;
                        long availableRoom = Long.parseLong(roomInfo.get("available_room").toString());
                        if (availableRoom > 0) {
                            String categoryId = roomInfo.get("category_id").toString();
                            bookingDetails.put("category_id", categoryId);
                            System.out.println(bookingDetails);
                            break; // Use the first available room
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingDetails; // Return details as a map
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
        assertTrue(responseTime < MAX_RESPONSE_TIME,
                "Response time exceeds the acceptable threshold of " + MAX_RESPONSE_TIME + " milliseconds");
    }

    // Validate the response body
    private static void validateResponseBody1WayOnWard(JSONObject response) {

        String responseBody = response.toString();
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


    private static void validateResponseBody2Day(JSONObject response) {

        String responseBody = response.toString();
        System.out.println("Response Body: " + responseBody);
        //Parameter validation
        JsonPath jsonPath = new JsonPath(responseBody);
        Object ForResponseParametersValidation_Status = jsonPath.get("status");
        assertThat(ForResponseParametersValidation_Status, equalTo("success"));
        System.out.println("******************************");

        // Validate TwoDaySailing_type field
        Object TwoDaySailing_type = jsonPath.get("2_day.sailing_type");

        if (TwoDaySailing_type != null && !TwoDaySailing_type.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'TwoDaySailing_type' has a valid value: " + TwoDaySailing_type);
            assertThat("Field 'TwoDaySailing_type' should have a valid value!",
                    TwoDaySailing_type.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'TwoDaySailing_type' is null or empty. Failing test case!");
            assertThat("Field 'TwoDaySailing_type' is null or empty!", TwoDaySailing_type, notNullValue());
            assertThat("Field 'TwoDaySailing_type' should not be empty!", TwoDaySailing_type.toString(), not(isEmptyString()));
        }
        // Validate '2_day.availability' field
        Object twoDayAvailability = jsonPath.get("2_day.availability");

        if (twoDayAvailability != null && !twoDayAvailability.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'twoDayAvailability' has a valid value: " + twoDayAvailability);
            assertThat("Field 'twoDayAvailability' should have a valid value!",
                    twoDayAvailability.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'twoDayAvailability' is null or empty. Failing test case!");
            assertThat("Field 'twoDayAvailability' is null or empty!", twoDayAvailability, notNullValue());
            assertThat("Field 'twoDayAvailability' should not be empty!", twoDayAvailability.toString(), not(isEmptyString()));
        }
    }
}