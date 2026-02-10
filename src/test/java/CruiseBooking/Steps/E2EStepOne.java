package CruiseBooking.Steps;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class E2EStepOne {

    private static final String URL = "https://bahamas.kapturecrm.com/v2-get-cruise-itinerary-availability";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 15000;

    @Test
    public void testGetItineraryAvailability() {
        JSONObject result = getItineraryAvailability();
        assertThat("Step 1 should return itineraries", result, notNullValue());
    }

    /**
     * Step 1: Get all itinerary availability across all cruises.
     * Sends only live_data=true to get all itineraries for cruises 61, 77, 102.
     * Returns the full parsed response containing session_id and available_itineries_details[].
     */
    @SuppressWarnings("unchecked")
    public static JSONObject getItineraryAvailability() {
        JSONObject payload = new JSONObject();
        payload.put("live_data", true);

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

        System.out.println("**************** E2E Step 1 Response ****************");
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);

        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(responseBody);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse Step 1 response", e);
        }
    }

    /**
     * Groups itineraries by cruise_id and picks the first itinerary per cruise.
     * Returns a map of cruiseId -> itinerary JSONObject.
     */
    public static Map<Long, JSONObject> pickOneItineraryPerCruise(JSONArray itineraries) {
        Map<Long, JSONObject> perCruise = new LinkedHashMap<>();
        for (Object obj : itineraries) {
            JSONObject itin = (JSONObject) obj;
            long cruiseId = parseLong(itin.get("cruise_id"));
            if (!perCruise.containsKey(cruiseId)) {
                perCruise.put(cruiseId, itin);
            }
        }
        return perCruise;
    }

    private static long parseLong(Object value) {
        if (value instanceof Long) return (Long) value;
        return Long.parseLong(value.toString());
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

        Object itineraries = jsonPath.get("available_itineries_details");
        assertThat("available_itineries_details should not be null", itineraries, notNullValue());
    }
}
