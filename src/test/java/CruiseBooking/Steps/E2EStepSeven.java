package CruiseBooking.Steps;

import CruiseBooking.Model.CabinConfig;
import CruiseBooking.Util.GuestDetailBuilder;
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

public class E2EStepSeven {

    private static final String URL = "https://bahamas.kapturecrm.com/v2-confirm-cruise-booking-guest-booking";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 18000;

    @Test
    public void testConfirmBooking() {
        CabinConfig defaultCabin = new CabinConfig(2, 0, 0);
        JSONObject result = confirmBooking(61, "09/15/2026", "2_day", "1", defaultCabin, "R001");
        assertThat("Step 7 should return booking confirmation", result, notNullValue());
    }

    /**
     * Step 7: Confirm cruise booking.
     * Sends the same guest_details[] as Step 6 to the confirm endpoint.
     * Returns the full parsed response containing booking_id and payment_link.
     */
    @SuppressWarnings("unchecked")
    public static JSONObject confirmBooking(long cruiseId, String sailingDate,
                                             String sailingType, String categoryId,
                                             CabinConfig cabin, String roomId) {
        JSONArray guestDetails = GuestDetailBuilder.buildGuestDetails(cabin, roomId);

        JSONObject payload = new JSONObject();
        payload.put("sailing_date", sailingDate);
        payload.put("sailing_type", sailingType);
        payload.put("cruise_id", String.valueOf(cruiseId));
        payload.put("category_id", categoryId);
        payload.put("total_room", 1);
        payload.put("guest_details", guestDetails);

        System.out.println("Request Body for Step 7:\n" + payload.toJSONString());

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

        System.out.println("**************** E2E Step 7 Response ****************");
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);

        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(responseBody);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse Step 7 response", e);
        }
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

        Object paymentLink = jsonPath.get("payment_link");
        assertThat("payment_link should not be empty",
                paymentLink.toString().trim(), not(isEmptyOrNullString()));

        Object bookingId = jsonPath.get("booking_id");
        assertThat("booking_id should not be empty",
                bookingId.toString().trim(), not(isEmptyOrNullString()));
    }
}
