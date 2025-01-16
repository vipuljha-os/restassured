package TestCases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class CruiseBookingStep1 {

    private static final String URL = "https://bahamas.kapturecrm.com/get-cruise-itinerary-availability";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 15000;


    public static void main(String[] arg) {
        verifyCruiseBookingStep1Test();
    }
    @Test
    public static JSONObject verifyCruiseBookingStep1Test() {
        // Generate a random year/month date after 6 months
        String randomYearMonth = generateRandomDateAfterSixMonths();
        System.out.println("**************");
        System.out.println(randomYearMonth);

        // Request body with dynamic year_month value
        String requestBody = "{\n" + "    \"itinerary_name\":\"2-Night Bahamas\",\n" + "    \"year_month\":\"" + randomYearMonth + "\"\n" + "}";
        // API Request
        Response response = given().header("Content-Type", CONTENT_TYPE).header("Authorization", AUTHORIZATION_HEADER).body(requestBody).when().post(URL);
        // Validating the Response
        validateStatusCode(response);
        validateResponseTime(response);
        validateResponseBody(response);

        System.out.println("**************** Step 1 response ****************");
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JSONParser parser = new JSONParser();
        JSONObject res = null;
        try {
            res = (JSONObject) parser.parse(responseBody);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray arr = (JSONArray) res.get("available_itineries_details");
        int size = arr.size();
        Random random = new Random();
        int randomNumber = random.nextInt(size);
        JSONObject obj = (JSONObject) arr.get(randomNumber);
        return obj;
    }

    // Method to generate a random date that is at least 6 months ahead and within the same year
    private static String generateRandomDateAfterSixMonths() {
        Random random = new Random();
        LocalDate currentDate = LocalDate.now();

        // Add 6 months to the current date
        LocalDate sixMonthsLater = currentDate.plusMonths(6);

        // Ensure the random date is within the same year as sixMonthsLater
        int startMonth = sixMonthsLater.getMonthValue();
        int endMonth = 12; // December is the last month of the same year

        int randomMonth = startMonth + random.nextInt(endMonth - startMonth + 1); // Random month within range
        int year = sixMonthsLater.getYear();

        // Format the date as YYYY/MM
        return String.format("%d/%02d", year, randomMonth);
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
        Object ForResponseParametersValidation_Message = jsonPath.get("message");
        assertThat(ForResponseParametersValidation_Message, equalTo("sailing-details found successfully"));
        System.out.println("******************************");

        // Validate 'itineraryName' field
        Object itineraryName = jsonPath.get("available_itineries_details[0].itinerary_name");

        if (itineraryName != null && !itineraryName.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'itineraryName' has a valid value: " + itineraryName);
            assertThat("Field 'itineraryName' should have a valid value!", itineraryName.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'itineraryName' is null or empty. Failing test case!");
            assertThat("Field 'itineraryName' is null or empty!", itineraryName, notNullValue());
            assertThat("Field 'itineraryName' should not be empty!", itineraryName.toString(), not(isEmptyString()));
        }
        // Validate 'cruiseName' field
        Object cruiseName = jsonPath.get("available_itineries_details[0].cruise_name");

        if (cruiseName != null && !cruiseName.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'cruiseName' has a valid value: " + cruiseName);
            assertThat("Field 'cruiseName' should have a valid value!", cruiseName.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'cruiseName' is null or empty. Failing test case!");
            assertThat("Field 'cruiseName' is null or empty!", cruiseName, notNullValue());
            assertThat("Field 'cruiseName' should not be empty!", cruiseName.toString(), not(isEmptyString()));
        }
        // Validate 'available_itineries_details' field
        Object availableItineriesDetails = jsonPath.get("available_itineries_details");

        if (availableItineriesDetails != null && !availableItineriesDetails.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'availableItineriesDetails' has a valid value: " + availableItineriesDetails);
            assertThat("Field 'availableItineriesDetails' should have a valid value!", availableItineriesDetails.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'availableItineriesDetails' is null or empty. Failing test case!");
            assertThat("Field 'availableItineriesDetails' is null or empty!", availableItineriesDetails, notNullValue());
            assertThat("Field 'availableItineriesDetails' should not be empty!", availableItineriesDetails.toString(), not(isEmptyString()));
        }

        // Validate 'available_itineries_details[0].itinerary_id' field
        Object availableItineriesDetailsItineraryID = jsonPath.get("available_itineries_details[0].itinerary_id");

        if (availableItineriesDetailsItineraryID != null && !availableItineriesDetailsItineraryID.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'availableItineriesDetailsItineraryID' has a valid value: " + availableItineriesDetailsItineraryID);
            assertThat("Field 'availableItineriesDetailsItineraryID' should have a valid value!", availableItineriesDetailsItineraryID.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'availableItineriesDetailsItineraryID' is null or empty. Failing test case!");
            assertThat("Field 'availableItineriesDetailsItineraryID' is null or empty!", availableItineriesDetailsItineraryID, notNullValue());
            assertThat("Field 'availableItineriesDetailsItineraryID' should not be empty!", availableItineriesDetailsItineraryID.toString(), not(isEmptyString()));
        }

        // Validate 'available_itineries_details[0].offers' field
        Object availableItineriesDetailsOffers = jsonPath.get("available_itineries_details[0].offers");

        if (availableItineriesDetailsOffers != null && !availableItineriesDetailsOffers.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'availableItineriesDetailsOffers' has a valid value: " + availableItineriesDetailsOffers);
            assertThat("Field 'availableItineriesDetailsOffers' should have a valid value!", availableItineriesDetailsOffers.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'availableItineriesDetailsOffers' is null or empty. Failing test case!");
            assertThat("Field 'availableItineriesDetailsOffers' is null or empty!", availableItineriesDetailsOffers, notNullValue());
            assertThat("Field 'availableItineriesDetailsOffers' should not be empty!", availableItineriesDetailsOffers.toString(), not(isEmptyString()));
        }

        // Validate '@.available_itineries_details[1].itinerary_id' field
        Object availableItineriesDetailsItineraryId = jsonPath.get("available_itineries_details[1].itinerary_id");

        if (availableItineriesDetailsItineraryId != null && !availableItineriesDetailsOffers.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'availableItineriesDetailsItineraryId' has a valid value: " + availableItineriesDetailsItineraryId);
            assertThat("Field 'availableItineriesDetailsItineraryId' should have a valid value!", availableItineriesDetailsItineraryId.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'availableItineriesDetailsItineraryId' is null or empty. Failing test case!");
            assertThat("Field 'availableItineriesDetailsItineraryId' is null or empty!", availableItineriesDetailsItineraryId, notNullValue());
            assertThat("Field 'availableItineriesDetailsItineraryId' should not be empty!", availableItineriesDetailsItineraryId.toString(), not(isEmptyString()));
        }
    }
}

