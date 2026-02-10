package CruiseBooking.Steps;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class E2EStepTwo {

    private static final String URL = "https://bahamas.kapturecrm.com/v2-itinerary-wise-sailing-details";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 18000;

    @Test
    public void testGetSailingDetails() {
        JSONObject step1Response = E2EStepOne.getItineraryAvailability();
        String sessionId = (String) step1Response.get("session_id");
        JSONArray itineraries = (JSONArray) step1Response.get("available_itineries_details");
        JSONObject firstItinerary = (JSONObject) itineraries.get(0);

        String itineraryTitle = extractItineraryTitle(firstItinerary);
        String yearMonth = extractYearMonth(firstItinerary);

        JSONObject result = getSailingDetails(itineraryTitle, sessionId, yearMonth);
        assertThat("Step 2 should return sailing details", result, notNullValue());
    }

    /**
     * Step 2: Get sailing details for a specific itinerary.
     * Returns the full parsed response containing sailing_details[].
     */
    @SuppressWarnings("unchecked")
    public static JSONObject getSailingDetails(String itineraryName, String sessionId, String yearMonth) {
        JSONObject payload = new JSONObject();
        payload.put("itinerary_name", itineraryName);
        payload.put("session_id", sessionId);
        payload.put("year_month", yearMonth);

        System.out.println("Request Body for Step 2:\n" + payload.toJSONString());

        Response response = given()
                .header("Content-Type", CONTENT_TYPE)
                .header("Authorization", AUTHORIZATION_HEADER)
                .body(payload.toJSONString())
                .when()
                .post(URL);

        response.then().log().all();

        validateStatusCode(response);
        validateResponseTime(response);
        validateResponseBody(response);

        System.out.println("**************** E2E Step 2 Response ****************");
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);

        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(responseBody);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse Step 2 response", e);
        }
    }

    /**
     * Extracts the itinerary title from a Step 1 itinerary object.
     * Tries "itinerary_title" first, falls back to "itinerary_name".
     */
    public static String extractItineraryTitle(JSONObject itinerary) {
        String title = (String) itinerary.get("itinerary_title");
        if (title == null) {
            title = (String) itinerary.get("itinerary_name");
        }
        return title;
    }

    /**
     * Extracts year/month from an itinerary's last_sailing_start_date.
     * Falls back to 6 months from now if parsing fails.
     */
    public static String extractYearMonth(JSONObject itinerary) {
        String lastSailingDate = (String) itinerary.get("last_sailing_start_date");
        if (lastSailingDate != null) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                Date date = inputFormat.parse(lastSailingDate);
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM");
                return outputFormat.format(date);
            } catch (Exception e) {
                System.out.println("Could not parse date: " + lastSailingDate + ". Using fallback.");
            }
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 6);
        return new SimpleDateFormat("yyyy/MM").format(cal.getTime());
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
    private static void validateResponseBody(Response response) {
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        Object status = jsonPath.get("status");
        assertThat(status, equalTo("success"));

        Object sessionId = jsonPath.get("session_id");
        assertThat("session_id should not be empty",
                sessionId.toString().trim(), not(isEmptyOrNullString()));

        Object sailingDetails = jsonPath.get("sailing_details");
        assertThat("sailing_details should not be null", sailingDetails, notNullValue());
    }
}
