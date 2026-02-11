package CruiseBooking.Util;

import CruiseBooking.Model.CabinConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GuestDetailBuilder {

    private static final DateTimeFormatter DOB_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @SuppressWarnings("unchecked")
    public static JSONArray buildGuestDetails(CabinConfig cabin, String roomId) {
        JSONArray guests = new JSONArray();

        int guestIndex = 1;

        for (int i = 0; i < cabin.getAdults(); i++) {
            guests.add(buildGuest(roomId, "Adult" + guestIndex, generateAdultDob(), guestIndex));
            guestIndex++;
        }

        for (int i = 0; i < cabin.getChildren(); i++) {
            guests.add(buildGuest(roomId, "Child" + guestIndex, generateChildDob(), guestIndex));
            guestIndex++;
        }

        for (int i = 0; i < cabin.getInfants(); i++) {
            guests.add(buildGuest(roomId, "Infant" + guestIndex, generateInfantDob(), guestIndex));
            guestIndex++;
        }

        return guests;
    }

    @SuppressWarnings("unchecked")
    private static JSONObject buildGuest(String roomId, String firstName, String dob, int index) {
        JSONObject guest = new JSONObject();
        guest.put("phone", 9876543440L + index);
        guest.put("dob", dob);
        guest.put("last_name", "TestGuest");
        guest.put("room_id", roomId);
        guest.put("title", "Mr.");
        guest.put("city", "NYC");
        guest.put("country", "USA");
        guest.put("nationality", "USA");
        guest.put("state", "Florida");
        guest.put("passport_no", "P" + (100000 + index));
        guest.put("passport_expiry_date", "04/02/2030");
        guest.put("first_name", firstName);
        guest.put("address", "1st Cross, 37/a, Test Address");
        guest.put("email", "cruisetest" + index + "@kapturecrm.com");
        guest.put("room_no", "0");
        guest.put("gender", "Male");
        return guest;
    }

    private static String generateAdultDob() {
        LocalDate dob = LocalDate.now().minusYears(30);
        return dob.format(DOB_FORMAT);
    }

    private static String generateChildDob() {
        LocalDate dob = LocalDate.now().minusYears(7);
        return dob.format(DOB_FORMAT);
    }

    private static String generateInfantDob() {
        LocalDate dob = LocalDate.now().minusYears(1);
        return dob.format(DOB_FORMAT);
    }
}
