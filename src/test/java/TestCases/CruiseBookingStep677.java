package TestCases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class CruiseBookingStep677 {
    private static final String URL = "https://bahamas.kapturecrm.com/get-cruise-booking-guest-booking-preview";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 18000;
//    @Test
//    public static void main(String[] args) {
//        VerifyCruiseBookingStep6();
//    }
@Test
public void testCruiseBookingStep6() {
    // Call the helper method to execute the API logic
    Object result = VerifyCruiseBookingStep6();
}
    public static Object VerifyCruiseBookingStep6() {
        CruiseBookingStep577 cruiseBookingStep5 = new CruiseBookingStep577();
        // Get the response of Step 1 (assuming the response object is stored in CruiseBookingStep1.responseOfStep1)
        JSONObject response1 = cruiseBookingStep5.VerifyCruiseBookingStep5();
        System.out.println("***********");


        // Get the room_id name
        String sailingDate = (String) response1.get("sailing_date");
        System.out.println("************" + sailingDate);

        // Get the room_id name
        String sailing_type = (String) response1.get("sailing_type");
        System.out.println("************" + sailing_type);

        JSONArray arr = (JSONArray) response1.get("room_details");
        String room_id = null;
        String categoryId = null;
        if (arr != null) {
            int size = arr.size();
            Random random = new Random();
            int randomNumber = random.nextInt(size);
            JSONObject obj = (JSONObject) arr.get(randomNumber);
            System.out.println(obj);

            // Get the room_id name
            room_id = (String) obj.get("room_id");
            System.out.println("************" + room_id);

            // Get the room_id name
            categoryId = (String) obj.get("category_id");
            System.out.println("************" + categoryId);

        }

        // Request body
        //String requestBody = "{\n" + "    \"sailing_date\": \"" + sailingDate + "\",\n" + "    \"sailing_type\": \"" + sailing_type + "\",\n" + "    \"cruise_id\": \"77\",\n" + "    \"category_id\": \"" + categoryId + "\",\n" + "    \"guest_details\": [\n" + "        {\n" + "            \"phone\": 9876543446,\n" + "            \"dob\": \"11/01/1990\",\n" + "            \"last_name\": \"Albert\",\n" + "            \"room_id\": \"" + room_id + "\",\n" + "            \"title\": \"Mr.\",\n" + "            \"city\": \"NYC\",\n" + "            \"country\": \"USA\",\n" + "            \"nationality\": \"USAA\",\n" + "            \"state\": \"Florida\",\n" + "            \"passport_no\": \"A298765\",\n" + "            \"passport_expiry_date\": \"04/02/2030\",\n" + "            \"first_name\": \"Qwerty\",\n" + "            \"address\": \"1st Cross dummy details, 37/a\",\n" + "            \"email\": \"yash.tembhare@kapturecrm.com\",\n" + "            \"room_no\": \"4037\",\n" + "            \"gender\": \"Male\"\n" + "        },\n" + "        {\n" + "            \"phone\": 9876543446,\n" + "            \"dob\": \"11/01/1990\",\n" + "            \"last_name\": \"Albert\",\n" + "            \"room_id\": \"" + room_id + "\",\n" + "            \"title\": \"Mr.\",\n" + "            \"city\": \"NYC\",\n" + "            \"country\": \"USA\",\n" + "            \"nationality\": \"USAA\",\n" + "            \"state\": \"Florida\",\n" + "            \"passport_no\": \"A298765\",\n" + "            \"passport_expiry_date\": \"04/02/2030\",\n" + "            \"first_name\": \"Pinto\",\n" + "            \"address\": \"1st Cross dummy details, 37/a\",\n" + "            \"email\": \"yash.tembhare@kapturecrm.com\",\n" + "            \"room_no\": \"4037\",\n" + "            \"gender\": \"Male\"\n" + "        }\n" + "    ],\n" + "    \"total_room\": 1\n" + "}";
          //String requestBody = "{\n" + "    \"sailing_date\": \"" + sailingDate + "\",\n" + "    \"sailing_type\": \"" + sailing_type + "\",\n" + "    \"cruise_id\": \"77\",\n" + "    \"category_id\": \"" + categoryId + "\",\n" + "    \"guest_details\": [\n" + "        {\n" + "            \"phone\": 9876543446,\n" + "            \"dob\": \"11/01/1990\",\n" + "            \"last_name\": \"Albert\",\n" + "            \"room_id\": \"" + room_id + "\",\n" + "            \"title\": \"Mr.\",\n" + "            \"city\": \"NYC\",\n" + "            \"country\": \"USA\",\n" + "            \"nationality\": \"USAA\",\n" + "            \"state\": \"Florida\",\n" + "            \"passport_no\": \"A298765\",\n" + "            \"passport_expiry_date\": \"04/02/2030\",\n" + "            \"first_name\": \"Qwerty\",\n" + "            \"address\": \"1st Cross dummy details, 37/a\",\n" + "            \"email\": \"yash.tembhare@kapturecrm.com\",\n" + "            \"room_no\": \"4037\",\n" + "            \"gender\": \"Male\"\n" + "        },\n" + "        {\n" + "            \"phone\": 9876543446,\n" + "            \"dob\": \"11/01/1990\",\n" + "            \"last_name\": \"Albert\",\n" + "            \"room_id\": \"" + room_id + "\",\n" + "            \"title\": \"Mr.\",\n" + "            \"city\": \"NYC\",\n" + "            \"country\": \"USA\",\n" + "            \"nationality\": \"USAA\",\n" + "            \"state\": \"Florida\",\n" + "            \"passport_no\": \"A298765\",\n" + "            \"passport_expiry_date\": \"04/02/2030\",\n" + "            \"first_name\": \"Pinto\",\n" + "            \"address\": \"1st Cross dummy details, 37/a\",\n" + "            \"email\": \"yash.tembhare@kapturecrm.com\",\n" + "            \"room_no\": \"4037\",\n" + "            \"gender\": \"Male\"\n" + "        }\n" + " {\n" + "            \"phone\": 9876543446,\n" + "            \"dob\": \"11/01/2018\",\n" + "            \"last_name\": \"Albert\",\n" + "            \"room_id\": \"" + room_id + "\",\n" + "            \"title\": \"Mr.\",\n" + "            \"city\": \"NYC\",\n" + "            \"country\": \"USA\",\n" + "            \"nationality\": \"USAA\",\n" + "            \"state\": \"Florida\",\n" + "            \"passport_no\": \"A298765\",\n" + "            \"passport_expiry_date\": \"04/02/2030\",\n" + "            \"first_name\": \"Qwerty\",\n" + "            \"address\": \"1st Cross dummy details, 37/a\",\n" + "            \"email\": \"yash.tembhare@kapturecrm.com\",\n" + "            \"room_no\": \"4037\",\n" + "            \"gender\": \"Male\"\n" + "        },\n"+"{\n" + "            \"phone\": 9876543446,\n" + "            \"dob\": \"11/01/2024\",\n" + "            \"last_name\": \"Albert\",\n" + "            \"room_id\": \"" + room_id + "\",\n" + "            \"title\": \"Mr.\",\n" + "            \"city\": \"NYC\",\n" + "            \"country\": \"USA\",\n" + "            \"nationality\": \"USAA\",\n" + "            \"state\": \"Florida\",\n" + "            \"passport_no\": \"A298765\",\n" + "            \"passport_expiry_date\": \"04/02/2030\",\n" + "            \"first_name\": \"Qwerty\",\n" + "            \"address\": \"1st Cross dummy details, 37/a\",\n" + "            \"email\": \"yash.tembhare@kapturecrm.com\",\n" + "            \"room_no\": \"4037\",\n" + "            \"gender\": \"Male\"\n" + "        },\n"   ],\n" + "    \"total_room\": 1\n" + "}";



        String requestBody="{\n" +
                "    \"sailing_date\": \"" + sailingDate + "\",\n" +
                "    \"sailing_type\":\"" + sailing_type + "\",\n" +
                "    \"cruise_id\": \"77\",\n" +
                "    \"category_id\": \"" + categoryId + "\",\n"  +
                "    \"guest_details\": [\n" +
                "        {\n" +
                "            \"room_id\": \"" + room_id + "\",\n"  +
                "            \"room_no\": \"4080\",\n" +
                "            \"first_name\": \"Surya\",\n" +
                "            \"last_name\": \"Kant\",\n" +
                "            \"email\": \"sktiwari25121999@gmail.com\",\n" +
                "            \"phone\": \"08009735929\",\n" +
                "            \"dob\": \"01/31/1985\",\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"country\": \"India\",\n" +
                "            \"state\": \"\",\n" +
                "            \"city\": \"\",\n" +
                "            \"zip_code\": \"234567\",\n" +
                "            \"address\": \"\",\n" +
                "            \"passport_no\": \"\",\n" +
                "            \"passport_expiry_date\": \"\",\n" +
                "            \"passport_issue_place\": \"\",\n" +
                "            \"green_card_no\": \"\",\n" +
                "            \"room_category_id\": \"187\",\n" +
                "            \"room_index\": \"1\",\n" +
                "            \"primary_guest\": \"true\",\n" +
                "            \"nationality\": \"India\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"room_id\": \"" + room_id + "\",\n"  +
                "            \"room_no\": \"4080\",\n" +
                "            \"first_name\": \"Nitish\",\n" +
                "            \"last_name\": \"Bhatnagar\",\n" +
                "            \"email\": \"sktiwari25121999@gmail.com\",\n" +
                "            \"phone\": \"\",\n" +
                "            \"dob\": \"02/02/1985\",\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"country\": \"India\",\n" +
                "            \"state\": \"\",\n" +
                "            \"city\": \"\",\n" +
                "            \"zip_code\": \"110032\",\n" +
                "            \"address\": \"\",\n" +
                "            \"passport_no\": \"\",\n" +
                "            \"passport_expiry_date\": \"\",\n" +
                "            \"passport_issue_place\": \"\",\n" +
                "            \"green_card_no\": \"\",\n" +
                "            \"room_category_id\": \"187\",\n" +
                "            \"room_index\": \"1\",\n" +
                "            \"nationality\": \"India\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"room_id\": \"" + room_id + "\",\n" +
                "            \"room_no\": \"4080\",\n" +
                "            \"first_name\": \"test\",\n" +
                "            \"last_name\": \"surya\",\n" +
                "            \"email\": \"sktiwari25121999@gmail.com\",\n" +
                "            \"phone\": \"\",\n" +
                "            \"dob\": \"01/29/2016\",\n" +
                "            \"gender\": \"Female\",\n" +
                "            \"country\": \"United States\",\n" +
                "            \"state\": \"\",\n" +
                "            \"city\": \"\",\n" +
                "            \"zip_code\": \"765432\",\n" +
                "            \"address\": \"\",\n" +
                "            \"passport_no\": \"\",\n" +
                "            \"passport_expiry_date\": \"\",\n" +
                "            \"passport_issue_place\": \"\",\n" +
                "            \"green_card_no\": \"\",\n" +
                "            \"room_category_id\": \"187\",\n" +
                "            \"room_index\": \"1\",\n" +
                "            \"nationality\": \"United States\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"room_id\":\"" + room_id + "\",\n" +
                "            \"room_no\": \"4080\",\n" +
                "            \"first_name\": \"testtttt\",\n" +
                "            \"last_name\": \"suryaaaaa\",\n" +
                "            \"email\": \"sktiwari25121999@gmail.com\",\n" +
                "            \"phone\": \"\",\n" +
                "            \"dob\": \"03/29/2024\",\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"country\": \"United States\",\n" +
                "            \"state\": \"\",\n" +
                "            \"city\": \"\",\n" +
                "            \"zip_code\": \"789876\",\n" +
                "            \"address\": \"\",\n" +
                "            \"passport_no\": \"\",\n" +
                "            \"passport_expiry_date\": \"\",\n" +
                "            \"passport_issue_place\": \"\",\n" +
                "            \"green_card_no\": \"\",\n" +
                "            \"room_category_id\": \"187\",\n" +
                "            \"room_index\": \"1\",\n" +
                "            \"nationality\": \"United States\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"total_room\": 1\n" +
                "}\n";

        System.out.println(requestBody);
        // API Request
        Response response = given().header("Content-Type", CONTENT_TYPE).header("Authorization", AUTHORIZATION_HEADER).body(requestBody).when().post(URL);

        // Logging the Response
        response.then().log().all();
        String responseBody = response.getBody().asString();
        System.out.println("**************** response of step 6 ****************");
        System.out.println(requestBody);
        // Validating the Response
        validateStatusCode(response);
        validateResponseTime(response);
        validateResponseBody(response);
        System.out.println("**************** response of step 5 ****************");
        System.out.println(response);
        return null;
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
        assertTrue(responseTime < MAX_RESPONSE_TIME, "Response time exceeds the acceptable threshold of " + MAX_RESPONSE_TIME + " milliseconds");
    }

    // Validate the response body
    private static void validateResponseBody(Response response) {
        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);
        //Parameter validation
        JsonPath jsonPath = new JsonPath(responseBody);
        Object ForResponseParametersValidation_Status = jsonPath.get("status");
        assertThat(ForResponseParametersValidation_Status, equalTo("success"));
        System.out.println("******************************");

        // Extract the 'room_id' field from the JSON response
        Object guestDetailsRoomId = jsonPath.get("guest_details[0].room_id");

// Check if the 'room_id' field is present and has a valid value
        if (guestDetailsRoomId != null && !guestDetailsRoomId.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'guestDetailsRoomId' has a valid value: " + guestDetailsRoomId);

            // Check if the value is numeric
            if (StringUtils.isNumeric(guestDetailsRoomId.toString())) {
                int roomIdValue = Integer.parseInt(guestDetailsRoomId.toString());

                // Check if the value is 0 or negative
                if (roomIdValue <= 0) {
                    System.out.println("Field 'guestDetailsRoomId' has a 0 or negative value: " + roomIdValue);
                    assertThat("Field 'guestDetailsRoomId' should not be 0 or negative!", roomIdValue, greaterThan(0));
                } else {
                    // Value is valid (not null, not empty, and positive)
                    System.out.println("Field 'guestDetailsRoomId' has a valid positive value: " + roomIdValue);
                }
            } else {
                // Value is not numeric
                System.out.println("Field 'guestDetailsRoomId' is not a numeric value: " + guestDetailsRoomId);
                assertThat("Field 'guestDetailsRoomId' should be a numeric value!", false);
            }
        } else {
            // Value is null or empty
            System.out.println("Field 'guestDetailsRoomId' is null or empty. Failing test case!");
            assertThat("Field 'guestDetailsRoomId' is null or empty!", guestDetailsRoomId, notNullValue());
            assertThat("Field 'guestDetailsRoomId' should not be empty!", guestDetailsRoomId.toString(), not(isEmptyString()));
        }

        // Validate '@.payment_detail[0].total' field
        Object paymentDetailTotal = jsonPath.get("payment_detail[0].total");

        if (paymentDetailTotal != null && !paymentDetailTotal.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'paymentDetailTotal' has a valid value: " + paymentDetailTotal);
            assertThat("Field 'paymentDetailTotal' should have a valid value!", paymentDetailTotal.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'paymentDetailTotal' is null or empty. Failing test case!");
            assertThat("Field 'paymentDetailTotal' is null or empty!", paymentDetailTotal, notNullValue());
            assertThat("Field 'paymentDetailTotal' should not be empty!", paymentDetailTotal.toString(), not(isEmptyString()));
        }
        // Extract the 'total_tax' field from the JSON response
        Object paymentDetailTotalTax = jsonPath.get("payment_detail[0].total_tax");

// Check if the 'total_tax' field is present and has a valid value
        if (paymentDetailTotalTax != null && !paymentDetailTotalTax.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'paymentDetailTotalTax' has a valid value: " + paymentDetailTotalTax);

            // Check if the value is numeric
            if (StringUtils.isNumeric(paymentDetailTotalTax.toString())) {
                double totalTaxValue = Double.parseDouble(paymentDetailTotalTax.toString());

                // Check if the value is negative
                if (totalTaxValue < 0) {
                    System.out.println("Field 'paymentDetailTotalTax' has a negative value: " + totalTaxValue);
                    assertThat("Field 'paymentDetailTotalTax' should not be negative!", totalTaxValue, greaterThanOrEqualTo(0.0));
                } else {
                    // Value is valid (not null, not empty, and non-negative)
                    System.out.println("Field 'paymentDetailTotalTax' has a valid non-negative value: " + totalTaxValue);
                }
            } else {
                // Value is not numeric
                System.out.println("Field 'paymentDetailTotalTax' is not a numeric value: " + paymentDetailTotalTax);
                //assertThat("Field 'paymentDetailTotalTax' should be a numeric value!", false);
            }
        } else {
            // Value is null or empty
            System.out.println("Field 'paymentDetailTotalTax' is null or empty. Failing test case!");
            assertThat("Field 'paymentDetailTotalTax' is null or empty!", paymentDetailTotalTax, notNullValue());
            assertThat("Field 'paymentDetailTotalTax' should not be empty!", paymentDetailTotalTax.toString(), not(isEmptyString()));
        }

        // Validate '@.payment_detail[0].price_detail[0].guest_index' field
        Object paymentDetailPriceDetailGuestIndex = jsonPath.get("payment_detail[0].price_detail[0].guest_index");

        if (paymentDetailPriceDetailGuestIndex != null && !paymentDetailPriceDetailGuestIndex.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'paymentDetailPriceDetailGuestIndex' has a valid value: " + paymentDetailPriceDetailGuestIndex);
            assertThat("Field 'paymentDetailPriceDetailGuestIndex' should have a valid value!", paymentDetailPriceDetailGuestIndex.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'paymentDetailPriceDetailGuestIndex' is null or empty. Failing test case!");
            assertThat("Field 'paymentDetailPriceDetailGuestIndex' is null or empty!", paymentDetailPriceDetailGuestIndex, notNullValue());
            assertThat("Field 'paymentDetailPriceDetailGuestIndex' should not be empty!", paymentDetailPriceDetailGuestIndex.toString(), not(isEmptyString()));
        }
        // Extract the 'payment_total' field from the JSON response
        Object paymentTotal = jsonPath.get("payment_total");

// Check if the 'payment_total' field is present and has a valid value
        if (paymentTotal != null && !paymentTotal.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'paymentTotal' has a valid value: " + paymentTotal);

            // Check if the value is numeric
            if (StringUtils.isNumeric(paymentTotal.toString())) {
                double paymentTotalValue = Double.parseDouble(paymentTotal.toString());

                // Check if the value is negative
                if (paymentTotalValue < 0) {
                    System.out.println("Field 'paymentTotal' has a negative value: " + paymentTotalValue);
                    assertThat("Field 'paymentTotal' should not be negative!", paymentTotalValue, greaterThanOrEqualTo(0.0));
                } else {
                    // Value is valid (not null, not empty, and non-negative)
                    System.out.println("Field 'paymentTotal' has a valid non-negative value: " + paymentTotalValue);
                }
            } else {
                // Value is not numeric
                System.out.println("Field 'paymentTotal' is not a numeric value: " + paymentTotal);
                //assertThat("Field 'paymentTotal' should be a numeric value!", false);
            }
        } else {
            // Value is null or empty
            System.out.println("Field 'paymentTotal' is null or empty. Failing test case!");
            assertThat("Field 'paymentTotal' is null or empty!", paymentTotal, notNullValue());
            assertThat("Field 'paymentTotal' should not be empty!", paymentTotal.toString(), not(isEmptyString()));
        }

    }

}

