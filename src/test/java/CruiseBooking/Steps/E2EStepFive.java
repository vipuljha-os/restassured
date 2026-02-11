package CruiseBooking.Steps;

import CruiseBooking.Model.CabinConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class E2EStepFive {

    private static final String URL = "https://bahamas.kapturecrm.com/v2-search-cruise-room-availability-block";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 18000;

    @Test
    public void testSearchRoomAvailability() {
        // This test is run via the E2E orchestrator with DataProvider.
        // Standalone test uses a default config for verification.
        CabinConfig defaultCabin = new CabinConfig(2, 0, 0);
        JSONObject result = searchRoomAvailability(61, "09/15/2026", "2_day", defaultCabin, "1");
        assertThat("Step 5 should return room availability", result, notNullValue());
    }

    /**
     * Step 5: Search cruise room availability and block.
     * Takes cruise details and a cabin config, returns the full parsed response
     * containing room_details[].
     */
    @SuppressWarnings("unchecked")
    public static JSONObject searchRoomAvailability(long cruiseId, String sailingDate,
                                                     String sailingType, CabinConfig cabin,
                                                     String categoryId) {
        JSONObject guestCount = new JSONObject();
        guestCount.put("adult", String.valueOf(cabin.getAdults()));
        guestCount.put("child", String.valueOf(cabin.getChildren()));
        guestCount.put("infant", String.valueOf(cabin.getInfants()));
        guestCount.put("category_id", categoryId);

        JSONArray guestCountDetails = new JSONArray();
        guestCountDetails.add(guestCount);

        JSONObject payload = new JSONObject();
        payload.put("cruise_id", String.valueOf(cruiseId));
        payload.put("total_room", "1");
        payload.put("sailing_date", sailingDate);
        payload.put("sailing_type", sailingType);
        payload.put("guest_count_details", guestCountDetails);

        System.out.println("Request Body for Step 5:\n" + payload.toJSONString());

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

        System.out.println("**************** E2E Step 5 Response ****************");
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);

        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(responseBody);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse Step 5 response", e);
        }
    }

    /**
     * Extracts the room_id from the first entry of room_details[].
     * Returns null if room_details is empty or missing.
     */
    public static String extractRoomId(JSONObject step5Response) {
        JSONArray roomDetails = (JSONArray) step5Response.get("room_details");
        if (roomDetails == null || roomDetails.isEmpty()) {
            return null;
        }
        JSONObject firstRoom = (JSONObject) roomDetails.get(0);
        return (String) firstRoom.get("room_id");
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

        Object roomDetails = jsonPath.get("room_details");
        assertThat("room_details should not be null", roomDetails, notNullValue());

        Object roomId = jsonPath.get("room_details[0].room_id");
        assertThat("room_id should not be empty",
                roomId.toString().trim(), not(isEmptyOrNullString()));

        Object roomPrice = jsonPath.get("room_details[0].price_detail.room_price");
        assertThat("room_price should not be empty",
                roomPrice.toString().trim(), not(isEmptyOrNullString()));

        Object roomTax = jsonPath.get("room_details[0].price_detail.room_tax");
        assertThat("room_tax should not be empty",
                roomTax.toString().trim(), not(isEmptyOrNullString()));

        Object total = jsonPath.get("room_details[0].price_detail.total");
        assertThat("total should not be empty",
                total.toString().trim(), not(isEmptyOrNullString()));
    }
}
