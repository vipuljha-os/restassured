package CruiseBooking.Api;

import CruiseBooking.Model.CabinConfig;
import CruiseBooking.Util.GuestDetailBuilder;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static io.restassured.RestAssured.given;

public class CruiseBookingApiClient {

    private static final String BASE_URL = "https://bahamas.kapturecrm.com";
    private static final String AUTH_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";

    // Step 1: Get all itineraries
    @SuppressWarnings("unchecked")
    public JSONObject getItineraryAvailability() {
        JSONObject payload = new JSONObject();
        payload.put("live_data", true);

        Response response = given()
                .header("Content-Type", CONTENT_TYPE)
                .header("Authorization", AUTH_HEADER)
                .body(payload.toJSONString())
                .when()
                .post(BASE_URL + "/v2-get-cruise-itinerary-availability");

        return parseResponse(response, "Step 1 - Get Itinerary Availability");
    }

    // Step 2: Get sailing details for an itinerary
    @SuppressWarnings("unchecked")
    public JSONObject getSailingDetails(String itineraryName, String sessionId, String yearMonth) {
        JSONObject payload = new JSONObject();
        payload.put("itinerary_name", itineraryName);
        payload.put("session_id", sessionId);
        payload.put("year_month", yearMonth);

        Response response = given()
                .header("Content-Type", CONTENT_TYPE)
                .header("Authorization", AUTH_HEADER)
                .body(payload.toJSONString())
                .when()
                .post(BASE_URL + "/v2-itinerary-wise-sailing-details");

        return parseResponse(response, "Step 2 - Get Sailing Details");
    }

    // Step 4: Get category availability
    @SuppressWarnings("unchecked")
    public JSONObject getCategoryAvailability(long cruiseId, String sailingDate, String sailingType) {
        JSONObject payload = new JSONObject();
        payload.put("cruise_id", cruiseId);
        payload.put("sailing_date", sailingDate);
        payload.put("sailing_type", sailingType);

        Response response = given()
                .header("Content-Type", CONTENT_TYPE)
                .header("Authorization", AUTH_HEADER)
                .body(payload.toJSONString())
                .when()
                .post(BASE_URL + "/v2-get-cruise-category-availability");

        return parseResponse(response, "Step 4 - Get Category Availability");
    }

    // Step 5: Search room availability
    @SuppressWarnings("unchecked")
    public JSONObject searchRoomAvailability(long cruiseId, String sailingDate,
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

        Response response = given()
                .header("Content-Type", CONTENT_TYPE)
                .header("Authorization", AUTH_HEADER)
                .body(payload.toJSONString())
                .when()
                .post(BASE_URL + "/v2-search-cruise-room-availability-block");

        return parseResponse(response, "Step 5 - Search Room Availability");
    }

    // Step 6: Booking preview
    @SuppressWarnings("unchecked")
    public JSONObject getBookingPreview(long cruiseId, String sailingDate,
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

        Response response = given()
                .header("Content-Type", CONTENT_TYPE)
                .header("Authorization", AUTH_HEADER)
                .body(payload.toJSONString())
                .when()
                .post(BASE_URL + "/v2-get-cruise-booking-guest-booking-preview");

        return parseResponse(response, "Step 6 - Booking Preview");
    }

    // Step 7: Confirm booking
    @SuppressWarnings("unchecked")
    public JSONObject confirmBooking(long cruiseId, String sailingDate,
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

        Response response = given()
                .header("Content-Type", CONTENT_TYPE)
                .header("Authorization", AUTH_HEADER)
                .body(payload.toJSONString())
                .when()
                .post(BASE_URL + "/v2-confirm-cruise-booking-guest-booking");

        return parseResponse(response, "Step 7 - Confirm Booking");
    }

    private JSONObject parseResponse(Response response, String stepName) {
        int statusCode = response.statusCode();
        String body = response.getBody().asString();

        System.out.println("=== " + stepName + " ===");
        System.out.println("Status Code: " + statusCode);
        System.out.println("Response: " + body);

        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject) parser.parse(body);
            json.put("_http_status_code", (long) statusCode);
            json.put("_response_time", response.getTime());
            return json;
        } catch (ParseException e) {
            JSONObject errorJson = new JSONObject();
            errorJson.put("_http_status_code", (long) statusCode);
            errorJson.put("_parse_error", body);
            errorJson.put("status", "error");
            return errorJson;
        }
    }
}
