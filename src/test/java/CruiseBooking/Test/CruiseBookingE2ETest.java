package CruiseBooking.Test;

import CruiseBooking.Api.CruiseBookingApiClient;
import CruiseBooking.Data.BookingDataProvider;
import CruiseBooking.Model.BookingResult;
import CruiseBooking.Model.BookingScenario;
import CruiseBooking.Report.BookingResultTracker;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CruiseBookingE2ETest {

    private final CruiseBookingApiClient apiClient = new CruiseBookingApiClient();
    private final BookingResultTracker tracker = BookingResultTracker.getInstance();

    @Test(dataProvider = "cruiseBookingScenarios", dataProviderClass = BookingDataProvider.class,
            description = "End-to-end cruise booking test")
    @Description("Tests cruise booking flow: Room Search -> Preview -> Confirm")
    public void testCruiseBooking(BookingScenario scenario) {
        System.out.println("\n====================================================");
        System.out.println("SCENARIO: " + scenario.getLabel());
        System.out.println("====================================================");

        BookingResult result = new BookingResult(scenario);
        SoftAssert softAssert = new SoftAssert();

        // Step 5: Search room availability
        String roomId = executeStep5(scenario, result, softAssert);

        // Step 6: Booking preview (only if Step 5 passed)
        if (roomId != null) {
            executeStep6(scenario, result, roomId, softAssert);
        }

        // Step 7: Confirm booking (only if Step 5 passed)
        if (roomId != null) {
            executeStep7(scenario, result, roomId, softAssert);
        }

        tracker.addResult(result);
        softAssert.assertAll();
    }

    @Step("Step 5: Search Room Availability - Cruise {scenario.cruiseId}, {scenario.cabinConfig}")
    private String executeStep5(BookingScenario scenario, BookingResult result, SoftAssert softAssert) {
        try {
            JSONObject response = apiClient.searchRoomAvailability(
                    scenario.getCruiseId(),
                    scenario.getSailingDate(),
                    scenario.getSailingType(),
                    scenario.getCabinConfig(),
                    scenario.getCategoryId()
            );

            String status = (String) response.get("status");
            if (!"success".equals(status)) {
                result.setStep5Status("FAIL");
                result.setErrorMessage("Step5: status=" + status);
                softAssert.fail("Step 5 failed: status=" + status);
                return null;
            }

            JSONArray roomDetails = (JSONArray) response.get("room_details");
            if (roomDetails == null || roomDetails.isEmpty()) {
                result.setStep5Status("FAIL");
                result.setErrorMessage("Step5: no room_details returned");
                softAssert.fail("Step 5 failed: no room_details");
                return null;
            }

            JSONObject firstRoom = (JSONObject) roomDetails.get(0);
            String roomId = (String) firstRoom.get("room_id");

            result.setStep5Status("PASS");
            System.out.println("Step 5 PASS - room_id: " + roomId);
            return roomId;

        } catch (Exception e) {
            result.setStep5Status("FAIL");
            result.setErrorMessage("Step5 exception: " + e.getMessage());
            softAssert.fail("Step 5 exception: " + e.getMessage());
            return null;
        }
    }

    @Step("Step 6: Booking Preview - Cruise {scenario.cruiseId}, {scenario.cabinConfig}")
    private void executeStep6(BookingScenario scenario, BookingResult result,
                              String roomId, SoftAssert softAssert) {
        try {
            JSONObject response = apiClient.getBookingPreview(
                    scenario.getCruiseId(),
                    scenario.getSailingDate(),
                    scenario.getSailingType(),
                    scenario.getCategoryId(),
                    scenario.getCabinConfig(),
                    roomId
            );

            String status = (String) response.get("status");
            if (!"success".equals(status)) {
                result.setStep6Status("FAIL");
                String existing = result.getErrorMessage();
                String msg = "Step6: status=" + status;
                result.setErrorMessage(existing != null ? existing + " | " + msg : msg);
                softAssert.fail("Step 6 failed: status=" + status);
                return;
            }

            result.setStep6Status("PASS");
            System.out.println("Step 6 PASS - Preview successful");

        } catch (Exception e) {
            result.setStep6Status("FAIL");
            String existing = result.getErrorMessage();
            String msg = "Step6 exception: " + e.getMessage();
            result.setErrorMessage(existing != null ? existing + " | " + msg : msg);
            softAssert.fail("Step 6 exception: " + e.getMessage());
        }
    }

    @Step("Step 7: Confirm Booking - Cruise {scenario.cruiseId}, {scenario.cabinConfig}")
    private void executeStep7(BookingScenario scenario, BookingResult result,
                              String roomId, SoftAssert softAssert) {
        try {
            JSONObject response = apiClient.confirmBooking(
                    scenario.getCruiseId(),
                    scenario.getSailingDate(),
                    scenario.getSailingType(),
                    scenario.getCategoryId(),
                    scenario.getCabinConfig(),
                    roomId
            );

            String status = (String) response.get("status");
            if (!"success".equals(status)) {
                result.setStep7Status("FAIL");
                String existing = result.getErrorMessage();
                String msg = "Step7: status=" + status;
                result.setErrorMessage(existing != null ? existing + " | " + msg : msg);
                softAssert.fail("Step 7 failed: status=" + status);
                return;
            }

            Object bookingId = response.get("booking_id");
            Object paymentLink = response.get("payment_link");

            result.setStep7Status("PASS");
            if (bookingId != null) result.setBookingId(bookingId.toString());
            if (paymentLink != null) result.setPaymentLink(paymentLink.toString());

            System.out.println("Step 7 PASS - booking_id: " + bookingId);

        } catch (Exception e) {
            result.setStep7Status("FAIL");
            String existing = result.getErrorMessage();
            String msg = "Step7 exception: " + e.getMessage();
            result.setErrorMessage(existing != null ? existing + " | " + msg : msg);
            softAssert.fail("Step 7 exception: " + e.getMessage());
        }
    }

    @AfterSuite
    public void generateReport() {
        tracker.writeReport();
    }
}
