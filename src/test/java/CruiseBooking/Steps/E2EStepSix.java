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

public class E2EStepSix {

    private static final String URL = "https://bahamas.kapturecrm.com/v2-get-cruise-booking-guest-booking-preview";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 18000;

    @Test
    public void testBookingPreview() {
        CabinConfig defaultCabin = new CabinConfig(2, 0, 0);
        JSONObject result = getBookingPreview(61, "09/15/2026", "2_day", "1", defaultCabin, "R001");
        assertThat("Step 6 should return booking preview", result, notNullValue());
    }

    /**
     * Step 6: Get cruise booking guest booking preview.
     * Builds guest_details[] dynamically from the CabinConfig and roomId.
     * Returns the full parsed response containing guest_details[], payment_detail[], payment_total.
     */
    @SuppressWarnings("unchecked")
    public static JSONObject getBookingPreview(long cruiseId, String sailingDate,
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

        System.out.println("Request Body for Step 6:\n" + payload.toJSONString());

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

        System.out.println("**************** E2E Step 6 Response ****************");
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);

        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(responseBody);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse Step 6 response", e);
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

        Object guestDetailsRoomId = jsonPath.get("guest_details[0].room_id");
        assertThat("guest_details[0].room_id should not be empty",
                guestDetailsRoomId.toString().trim(), not(isEmptyOrNullString()));

        Object paymentTotal = jsonPath.get("payment_total");
        assertThat("payment_total should not be empty",
                paymentTotal.toString().trim(), not(isEmptyOrNullString()));

        Object paymentDetailTotal = jsonPath.get("payment_detail[0].total");
        assertThat("payment_detail[0].total should not be empty",
                paymentDetailTotal.toString().trim(), not(isEmptyOrNullString()));

        Object paymentDetailTotalTax = jsonPath.get("payment_detail[0].total_tax");
        assertThat("payment_detail[0].total_tax should not be empty",
                paymentDetailTotalTax.toString().trim(), not(isEmptyOrNullString()));
    }
}
