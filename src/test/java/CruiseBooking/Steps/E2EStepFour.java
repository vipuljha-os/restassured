package CruiseBooking.Steps;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class E2EStepFour {

    private static final String URL = "https://bahamas.kapturecrm.com/v2-get-cruise-category-availability";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 18000;

    @Test
    public void testGetCategoryAvailability() {
        // Chain from Steps 1 -> 2 -> 4
        JSONObject step1Response = E2EStepOne.getItineraryAvailability();
        String sessionId = (String) step1Response.get("session_id");
        JSONArray itineraries = (JSONArray) step1Response.get("available_itineries_details");
        JSONObject firstItinerary = (JSONObject) itineraries.get(0);

        long cruiseId = parseLong(firstItinerary.get("cruise_id"));
        String itineraryTitle = E2EStepTwo.extractItineraryTitle(firstItinerary);
        String yearMonth = E2EStepTwo.extractYearMonth(firstItinerary);

        JSONObject step2Response = E2EStepTwo.getSailingDetails(itineraryTitle, sessionId, yearMonth);
        JSONArray sailings = (JSONArray) step2Response.get("sailing_details");
        JSONObject firstSailing = (JSONObject) sailings.get(0);

        String sailingDate = (String) firstSailing.get("sailing_date");
        String sailingType = (String) firstSailing.get("sailing_type");

        JSONObject result = getCategoryAvailability(cruiseId, sailingDate, sailingType);
        assertThat("Step 4 should return category availability", result, notNullValue());
    }

    /**
     * Step 4: Get cruise category availability for a specific sailing.
     * Returns the full parsed response containing sailing type objects (2_day / 1_way_onward)
     * with availability[] arrays.
     */
    @SuppressWarnings("unchecked")
    public static JSONObject getCategoryAvailability(long cruiseId, String sailingDate, String sailingType) {
        JSONObject payload = new JSONObject();
        payload.put("cruise_id", cruiseId);
        payload.put("sailing_date", sailingDate);
        payload.put("sailing_type", sailingType);

        System.out.println("Request Body for Step 4:\n" + payload.toJSONString());

        Response response = given()
                .header("Content-Type", CONTENT_TYPE)
                .header("Authorization", AUTHORIZATION_HEADER)
                .body(payload.toJSONString())
                .when()
                .post(URL);

        response.then().log().all();

        validateStatusCode(response);
        validateResponseTime(response);

        System.out.println("**************** E2E Step 4 Response ****************");
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);

        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(responseBody);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse Step 4 response", e);
        }
    }

    /**
     * Extracts the first category_id with available_room > 0 from Step 4 response.
     * Checks "2_day" first, then "1_way_onward".
     * Returns null if no available category found.
     */
    public static String extractFirstAvailableCategory(JSONObject step4Response) {
        String[] sailingKeys = {"2_day", "1_way_onward"};

        for (String key : sailingKeys) {
            JSONObject sailingObj = (JSONObject) step4Response.get(key);
            if (sailingObj == null) continue;

            JSONArray availability = (JSONArray) sailingObj.get("availability");
            if (availability == null) continue;

            for (Object obj : availability) {
                JSONObject room = (JSONObject) obj;
                long availableRoom = Long.parseLong(room.get("available_room").toString());
                if (availableRoom > 0) {
                    return room.get("category_id").toString();
                }
            }
        }
        return null;
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
}
