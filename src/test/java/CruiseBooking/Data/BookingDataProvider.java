package CruiseBooking.Data;

import CruiseBooking.Model.BookingScenario;
import CruiseBooking.Model.CabinConfig;
import CruiseBooking.Steps.E2EStepFour;
import CruiseBooking.Steps.E2EStepOne;
import CruiseBooking.Steps.E2EStepTwo;
import CruiseBooking.Util.GuestComboGenerator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookingDataProvider {

    @DataProvider(name = "cruiseBookingScenarios")
    public static Object[][] getCruiseBookingScenarios() {
        List<BookingScenario> scenarios = new ArrayList<>();

        // Step 1: Get all itineraries across all cruises
        JSONObject step1Response = E2EStepOne.getItineraryAvailability();
        String sessionId = (String) step1Response.get("session_id");
        JSONArray allItineraries = (JSONArray) step1Response.get("available_itineries_details");

        if (allItineraries == null || allItineraries.isEmpty()) {
            throw new RuntimeException("Step 1 returned no itineraries");
        }

        // Group by cruise_id, pick first itinerary per cruise
        Map<Long, JSONObject> onePerCruise = E2EStepOne.pickOneItineraryPerCruise(allItineraries);

        System.out.println("=== Cruises found: " + onePerCruise.keySet() + " ===");

        for (Map.Entry<Long, JSONObject> entry : onePerCruise.entrySet()) {
            long cruiseId = entry.getKey();
            JSONObject itinerary = entry.getValue();

            String itineraryTitle = E2EStepTwo.extractItineraryTitle(itinerary);
            String yearMonth = E2EStepTwo.extractYearMonth(itinerary);

            System.out.println("=== Processing cruise " + cruiseId
                    + ", itinerary: " + itineraryTitle + " ===");

            // Step 2: Get sailing details for this itinerary
            JSONObject step2Response = E2EStepTwo.getSailingDetails(itineraryTitle, sessionId, yearMonth);
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
            JSONObject step4Response = E2EStepFour.getCategoryAvailability(cruiseId, sailingDate, sailingType);
            String categoryId = E2EStepFour.extractFirstAvailableCategory(step4Response);

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
}
