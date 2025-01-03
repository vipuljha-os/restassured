package TestCases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.isEmptyString;
import static org.testng.Assert.assertTrue;

public class CruiseBookingStep6 {
    private static final String URL = "https://bahamas.kapturecrm.com/get-cruise-booking-guest-booking-preview";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 18000;

    @Test
    public void VerifyCruiseBookingStep6() {
        // Request body
        String requestBody = "{\n" +
                "    \"sailing_date\": \"07/18/2025\",\n" +
                "    \"sailing_type\": \"2_day\",\n" +
                "    \"cruise_id\": \"61\",\n" +
                "    \"category_id\": \"183\",\n" +
                "    \"guest_details\": [\n" +
                "        {\n" +
                "            \"phone\": 9876543446,\n" +
                "            \"dob\": \"11/01/1990\",\n" +
                "            \"last_name\": \"Albert\",\n" +
                "            \"room_id\": \"6090\",\n" +
                "            \"title\": \"Mr.\",\n" +
                "            \"city\": \"NYC\",\n" +
                "            \"country\": \"USA\",\n" +
                "            \"nationality\": \"USAA\",\n" +
                "            \"state\": \"Florida\",\n" +
                "            \"passport_no\": \"A298765\",\n" +
                "            \"passport_expiry_date\": \"04/02/2030\",\n" +
                "            \"first_name\": \"Qwerty\",\n" +
                "            \"address\": \"1st Cross dummy details, 37/a\",\n" +
                "            \"email\": \"yash.tembhare@kapturecrm.com\",\n" +
                "            \"room_no\": \"4009\",\n" +
                "            \"gender\": \"Male\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"phone\": 9876543446,\n" +
                "            \"dob\": \"11/01/1990\",\n" +
                "            \"last_name\": \"Albert\",\n" +
                "            \"room_id\": \"6090\",\n" +
                "            \"title\": \"Mr.\",\n" +
                "            \"city\": \"NYC\",\n" +
                "            \"country\": \"USA\",\n" +
                "            \"nationality\": \"USAA\",\n" +
                "            \"state\": \"Florida\",\n" +
                "            \"passport_no\": \"A298765\",\n" +
                "            \"passport_expiry_date\": \"04/02/2030\",\n" +
                "            \"first_name\": \"Pinto\",\n" +
                "            \"address\": \"1st Cross dummy details, 37/a\",\n" +
                "            \"email\": \"yash.tembhare@kapturecrm.com\",\n" +
                "            \"room_no\": \"4009\",\n" +
                "            \"gender\": \"Male\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"total_room\": 1\n" +
                "}";
        // API Request
        Response response = given()
                .header("Content-Type", CONTENT_TYPE)
                .header("Authorization", AUTHORIZATION_HEADER)
                .body(requestBody)
                .when()
                .post(URL);

        // Logging the Response
        response.then().log().all();

        // Validating the Response
        validateStatusCode(response);
        validateResponseTime(response);
        validateResponseBody(response);
    }

    // Validate the status code
    private void validateStatusCode(Response response) {
        int statusCode = response.statusCode();
        System.out.println("Status Code: " + statusCode);
        response.then().statusCode(200);
    }

    // Validate the response time
    private void validateResponseTime(Response response) {
        long responseTime = response.getTime();
        System.out.println("Response Time: " + responseTime + "ms");
        assertTrue(responseTime < MAX_RESPONSE_TIME,
                "Response time exceeds the acceptable threshold of " + MAX_RESPONSE_TIME + " milliseconds");
    }

    // Validate the response body
    private void validateResponseBody(Response response) {
        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);
        //Parameter validation
        JsonPath jsonPath = new JsonPath(responseBody);
        Object ForResponseParametersValidation_Status = jsonPath.get("status");
        assertThat(ForResponseParametersValidation_Status, equalTo("success"));
        System.out.println("******************************");

        // Validate '@.guest_details[0].room_id' field
        Object guestDetailsRoomId = jsonPath.get("guest_details[0].room_id");

        if (guestDetailsRoomId != null && !guestDetailsRoomId.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'guestDetailsRoomId' has a valid value: " + guestDetailsRoomId);
            assertThat("Field 'guestDetailsRoomId' should have a valid value!",
                    guestDetailsRoomId.toString().trim(), not(isEmptyOrNullString()));
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
            assertThat("Field 'paymentDetailTotal' should have a valid value!",
                    paymentDetailTotal.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'paymentDetailTotal' is null or empty. Failing test case!");
            assertThat("Field 'paymentDetailTotal' is null or empty!", paymentDetailTotal, notNullValue());
            assertThat("Field 'paymentDetailTotal' should not be empty!", paymentDetailTotal.toString(), not(isEmptyString()));
        }
        // Validate '@.payment_detail[0].total_tax' field
        Object paymentDetailTotalTax = jsonPath.get("payment_detail[0].total_tax");

        if (paymentDetailTotalTax != null && !paymentDetailTotalTax.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'paymentDetailTotalTax' has a valid value: " + paymentDetailTotalTax);
            assertThat("Field 'paymentDetailTotalTax' should have a valid value!",
                    paymentDetailTotalTax.toString().trim(), not(isEmptyOrNullString()));
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
            assertThat("Field 'paymentDetailPriceDetailGuestIndex' should have a valid value!",
                    paymentDetailPriceDetailGuestIndex.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'paymentDetailPriceDetailGuestIndex' is null or empty. Failing test case!");
            assertThat("Field 'paymentDetailPriceDetailGuestIndex' is null or empty!", paymentDetailPriceDetailGuestIndex, notNullValue());
            assertThat("Field 'paymentDetailPriceDetailGuestIndex' should not be empty!", paymentDetailPriceDetailGuestIndex.toString(), not(isEmptyString()));
        }
        // Validate '@.payment_total' field
        Object paymentTotal = jsonPath.get("payment_total");

        if (paymentTotal != null && !paymentTotal.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'paymentTotal' has a valid value: " + paymentTotal);
            assertThat("Field 'paymentTotal' should have a valid value!",
                    paymentTotal.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'paymentTotal' is null or empty. Failing test case!");
            assertThat("Field 'paymentTotal' is null or empty!", paymentTotal, notNullValue());
            assertThat("Field 'paymentTotal' should not be empty!", paymentTotal.toString(), not(isEmptyString()));
        }
    }

}

