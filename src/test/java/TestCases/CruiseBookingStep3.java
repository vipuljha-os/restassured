package TestCases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class CruiseBookingStep3 {
    private static final String URL = "https://bahamas.kapturecrm.com/cruise-sailing-pricing-details";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 30000;

    //    @Test
//    public static void main(String[] arg) {
//        CruiseBookingStep3();
//    }
    @Test
    public void testCruiseBookingStep3() {
        // Call the helper method to execute the API logic
        Object result = CruiseBookingStep3();
    }

    public static Object CruiseBookingStep3() {
        // Request body
        String requestBody = "{\n" + "    \"fare_type\": \"retail\",\n" + "    \"live_data\": true,\n" + "    \"cruise_ids\": \"61\"\n" + "}";

        // API Request
        Response response = given().header("Authorization", AUTHORIZATION_HEADER).header("Content-Type", CONTENT_TYPE).body(requestBody).when().post(URL);

        // Logging the Response
        response.then().log().all();

        System.out.println("**************** response of step 3 ****************");
        System.out.println(response);

        // Validating the Response
        validateStatusCode(response);
        validateResponseTime(response);
        validateResponseBody(response);
        String responseBody = response.getBody().asString();

        JSONParser parser = new JSONParser();
        JSONObject res = null;
        try {
            res = (JSONObject) parser.parse(responseBody);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray arr = (JSONArray) res.get("sailings");
        int size = arr.size();
        Random random = new Random();
        int randomNumber = random.nextInt(size);
        JSONObject obj = (JSONObject) arr.get(randomNumber);
        return obj;
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
        assertTrue(responseTime < MAX_RESPONSE_TIME, "Response time exceeds the acceptable threshold of " + MAX_RESPONSE_TIME + " milliseconds");
    }

    // Validate the response body
    private static void validateResponseBody(Response response) {
        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);
        //Parameter validation
        JsonPath jsonPath = new JsonPath(responseBody);
        Object ForResponseParametersValidation_Status = jsonPath.get("status");
        assertThat(ForResponseParametersValidation_Status, equalTo("success"));
        System.out.println("******************************");


        // Extract the 'sailingsAvailablityRoomPrice' field from the JSON response
        Object sailingsAvailablityRoomPrice = jsonPath.get("sailings[0].availablity[0].room_price");

// Check if the 'sailingsAvailablityRoomPrice' field is present and has a valid value
        if (sailingsAvailablityRoomPrice != null && !sailingsAvailablityRoomPrice.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'sailingsAvailablityRoomPrice' has a valid value: " + sailingsAvailablityRoomPrice);

            // Check if the value is numeric
            if (StringUtils.isNumeric(sailingsAvailablityRoomPrice.toString())) {
                double roomPrice = Double.parseDouble(sailingsAvailablityRoomPrice.toString());

                // Check if the value is 0 or negative
                if (roomPrice <= 0) {
                    System.out.println("Field 'sailingsAvailablityRoomPrice' has a 0 or negative value: " + roomPrice);
                    assertThat("Field 'sailingsAvailablityRoomPrice' should not be 0 or negative!", roomPrice, greaterThan(0.0));
                } else {
                    // Value is valid (not null, not empty, and positive)
                    System.out.println("Field 'sailingsAvailablityRoomPrice' has a valid positive value: " + roomPrice);
                }
            } else {
                // Value is not numeric
                System.out.println("Field 'sailingsAvailablityRoomPrice' is not a numeric value: " + sailingsAvailablityRoomPrice);
                //assertThat("Field 'sailingsAvailablityRoomPrice' should be a numeric value!", false);
            }
        } else {
            // Value is null or empty
            System.out.println("Field 'sailingsAvailablityRoomPrice' is null or empty. Failing test case!");
            assertThat("Field 'sailingsAvailablityRoomPrice' is null or empty!", sailingsAvailablityRoomPrice, notNullValue());
            assertThat("Field 'sailingsAvailablityRoomPrice' should not be empty!", sailingsAvailablityRoomPrice.toString(), not(isEmptyString()));
        }


        // Validate '@.sailings[0].availablity[0].single_room_price_offer' field
        Object sailingsavailablitysingleroompriceoffer = jsonPath.get("sailings[0].availablity[0].single_room_price_offer");

        if (sailingsavailablitysingleroompriceoffer != null && !sailingsavailablitysingleroompriceoffer.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'sailingsavailablitysingleroompriceoffer' has a valid value: " + sailingsavailablitysingleroompriceoffer);
            assertThat("Field 'sailingsavailablitysingleroompriceoffer' should have a valid value!", sailingsavailablitysingleroompriceoffer.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'sailingsavailablitysingleroompriceoffer' is null or empty. Failing test case!");
            assertThat("Field 'sailingsavailablitysingleroompriceoffer' is null or empty!", sailingsavailablitysingleroompriceoffer, notNullValue());
            assertThat("Field 'sailingsavailablitysingleroompriceoffer' should not be empty!", sailingsavailablitysingleroompriceoffer.toString(), not(isEmptyString()));
        }

        // Validate 'sailings[0].availablity[0].taxes_per_person' field
        Object sailingsavailablitytaxesperperson = jsonPath.get("sailings[0].availablity[0].single_room_price_offer");

        if (sailingsavailablitytaxesperperson != null && !sailingsavailablitytaxesperperson.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'sailingsavailablitytaxesperperson' has a valid value: " + sailingsavailablitytaxesperperson);
            assertThat("Field 'sailingsavailablitytaxesperperson' should have a valid value!", sailingsavailablitytaxesperperson.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'sailingsavailablitytaxesperperson' is null or empty. Failing test case!");
            assertThat("Field 'sailingsavailablitytaxesperperson' is null or empty!", sailingsavailablitytaxesperperson, notNullValue());
            assertThat("Field 'sailingsavailablitytaxesperperson' should not be empty!", sailingsavailablitytaxesperperson.toString(), not(isEmptyString()));
        }
    }
}
