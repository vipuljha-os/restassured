package CruiseBooking.Data;

import CruiseBooking.Api.CruiseBookingApiClient;
import CruiseBooking.Model.BookingScenario;
import CruiseBooking.Model.CabinConfig;
import CruiseBooking.Util.GuestComboGenerator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;

import java.text.SimpleDateFormat;
import java.util.*;

public class BookingDataProvider {

    @DataProvider(name = "cruiseBookingScenarios")
    public static Object[][] getCruiseBookingScenarios() {
        CruiseBookingApiClient apiClient = new CruiseBookingApiClient();
        List<BookingScenario> scenarios = new ArrayList<>();

        // Step 1: Get all itineraries
        JSONObject step1Response = apiClient.getItineraryAvailability();
        String sessionId = (String) step1Response.get("session_id");
        JSONArray allItineraries = (JSONArray) step1Response.get("available_itineries_details");

        if (allItineraries == null || allItineraries.isEmpty()) {
            throw new RuntimeException("Step 1 returned no itineraries");
        }

        // Group itineraries by cruise_id, pick first itinerary per cruise
        Map<Long, JSONObject> onPerCruise = pickOneItineraryPerCruise(allItineraries);

        System.out.println("=== Cruises found: " + onPerCruise.keySet() + " ===");

        for (Map.Entry<Long, JSONObject> entry : onPerCruise.entrySet()) {
            long cruiseId = entry.getKey();
            JSONObject itinerary = entry.getValue();

            String itineraryTitle = (String) itinerary.get("itinerary_title");
            if (itineraryTitle == null) {
                itineraryTitle = (String) itinerary.get("itinerary_name");
            }

            String yearMonth = extractYearMonth(itinerary);

            System.out.println("=== Processing cruise " + cruiseId
                    + ", itinerary: " + itineraryTitle + " ===");

            // Step 2: Get sailing details for this itinerary
            JSONObject step2Response = apiClient.getSailingDetails(itineraryTitle, sessionId, yearMonth);
            JSONArray sailings = (JSONArray) step2Response.get("sailing_details");

            if (sailings == null || sailings.isEmpty()) {
                System.out.println("No sailings found for cruise " + cruiseId
                        + ", itinerary " + itineraryTitle + ". Skipping.");
                continue;
            }

            // Pick first sailing
            JSONObject sailing = (JSONObject) sailings.get(0);
            String sailingDate = (String) sailing.get("sailing_date");
            String sailingType = (String) sailing.get("sailing_type");

            System.out.println("=== Selected sailing: " + sailingDate
                    + " (" + sailingType + ") ===");

            // Step 4: Get category availability
            JSONObject step4Response = apiClient.getCategoryAvailability(cruiseId, sailingDate, sailingType);
            String categoryId = extractFirstAvailableCategory(step4Response);

            if (categoryId == null) {
                System.out.println("No available category for cruise " + cruiseId
                        + ", sailing " + sailingDate + ". Skipping.");
                continue;
            }

            System.out.println("=== Selected category: " + categoryId + " ===");

            // Generate scenarios for all 20 guest combos
            for (CabinConfig combo : GuestComboGenerator.getAllCombos()) {
                scenarios.add(new BookingScenario(
                        cruiseId, itineraryTitle, sessionId,
                        sailingDate, sailingType, categoryId, combo
                ));
            }
        }

        if (scenarios.isEmpty()) {
            throw new RuntimeException("No booking scenarios could be generated. "
                    + "Check if APIs are returning valid data.");
        }

        System.out.println("=== Total scenarios generated: " + scenarios.size() + " ===");

        // Convert to Object[][] for TestNG DataProvider
        Object[][] data = new Object[scenarios.size()][1];
        for (int i = 0; i < scenarios.size(); i++) {
            data[i][0] = scenarios.get(i);
        }
        return data;
    }

    private static Map<Long, JSONObject> pickOneItineraryPerCruise(JSONArray itineraries) {
        Map<Long, JSONObject> perCruise = new LinkedHashMap<>();
        for (Object obj : itineraries) {
            JSONObject itin = (JSONObject) obj;
            Object cruiseIdObj = itin.get("cruise_id");
            long cruiseId;
            if (cruiseIdObj instanceof Long) {
                cruiseId = (Long) cruiseIdObj;
            } else {
                cruiseId = Long.parseLong(cruiseIdObj.toString());
            }
            if (!perCruise.containsKey(cruiseId)) {
                perCruise.put(cruiseId, itin);
            }
        }
        return perCruise;
    }

    private static String extractYearMonth(JSONObject itinerary) {
        // Try to extract year/month from the itinerary's sailing date
        String lastSailingDate = (String) itinerary.get("last_sailing_start_date");
        if (lastSailingDate != null) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                Date date = inputFormat.parse(lastSailingDate);
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM");
                return outputFormat.format(date);
            } catch (Exception e) {
                System.out.println("Could not parse date: " + lastSailingDate
                        + ". Using fallback.");
            }
        }

        // Fallback: 6 months from now
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 6);
        return new SimpleDateFormat("yyyy/MM").format(cal.getTime());
    }

    private static String extractFirstAvailableCategory(JSONObject step4Response) {
        // Check "2_day" first, then "1_way_onward"
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
}
