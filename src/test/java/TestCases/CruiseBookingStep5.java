package TestCases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;
import java.util.Random;

import static TestCases.CruiseBookingStep4.extractCruiseBookingDetails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class CruiseBookingStep5 {
    private static final String URL = "https://bahamas.kapturecrm.com/search-cruise-room-availability-block";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 18000;

    public static void main(String[] arg) {
        VerifyCruiseBookingStep5();
    }

    public static JSONObject VerifyCruiseBookingStep5() {
        CruiseBookingStep4 cruiseBookingStep4 = new CruiseBookingStep4();
        JSONObject response1 = cruiseBookingStep4.VerifyCruiseBookingStep4();

        // Extract required booking details
        Map<String, String> bookingDetails = extractCruiseBookingDetails(response1);

        // Extract individual parameters
        String sailingType = bookingDetails.get("sailing_type");
        String sailingDate = bookingDetails.get("sailing_date");
        String categoryId = bookingDetails.get("category_id");
        //  long cruiseId = (long) response1.get("cruise_id");

        System.out.println("Sailing Type: " + sailingType);
        System.out.println("Sailing Date: " + sailingDate);
        System.out.println("Category ID: " + categoryId);
        // System.out.println("Cruise ID: " + cruiseId);

        // Prepare the request body
        String requestBody = "{\n" +
                "    \"cruise_id\":\"61\",\n" +
                "    \"total_room\": \"1\",\n" +
                "    \"sailing_date\": \"" + sailingDate + "\",\n" +
                "    \"sailing_type\": \"" + sailingType + "\",\n" +
                "    \"guest_count_details\": [\n" +
                "        {\n" +
                "            \"adult\": \"2\",\n" +
                "            \"child\": \"0\",\n" +
                "            \"infant\": \"0\",\n" +
                "            \"category_id\":\"" + categoryId + "\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        System.out.println("Request Body for Step 5:\n" + requestBody);

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
        //return 0;

        String responseBody = response.getBody().asString();
        System.out.println("**************** response of step 5 ****************");
        System.out.println(requestBody);
        JSONParser parser = new JSONParser();
        JSONObject res = null;
        try {
            res = (JSONObject) parser.parse(responseBody);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray arr = (JSONArray) res.get("room_details");
        if (arr != null) {
            int size = arr.size();
            Random random = new Random();
            int randomNumber = random.nextInt(size);
            JSONObject obj = (JSONObject) arr.get(randomNumber);
            System.out.println(obj);
            return res;
        }
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
        assertTrue(responseTime < MAX_RESPONSE_TIME,
                "Response time exceeds the acceptable threshold of " + MAX_RESPONSE_TIME + " milliseconds");
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

        // Validate '@.room_details[0].room_id' field
        Object roomdetailsroomId = jsonPath.get("room_details[0].room_id");

        if (roomdetailsroomId != null && !roomdetailsroomId.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'sessionId' has a valid value: " + roomdetailsroomId);
            assertThat("Field 'sessionId' should have a valid value!",
                    roomdetailsroomId.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'roomdetailsroomId' is null or empty. Failing test case!");
            assertThat("Field 'roomdetailsroomId' is null or empty!", roomdetailsroomId, notNullValue());
            assertThat("Field 'roomdetailsroomId' should not be empty!", roomdetailsroomId.toString(), not(isEmptyString()));
        }

        // Validate '@.room_details[0].price_detail.room_price' field
        Object roomDetailsPriceDetailRoomPrice = jsonPath.get("room_details[0].price_detail.room_price");

        if (roomDetailsPriceDetailRoomPrice != null && !roomDetailsPriceDetailRoomPrice.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'roomDetailsPriceDetailRoomPrice' has a valid value: " + roomDetailsPriceDetailRoomPrice);
            assertThat("Field 'roomDetailsPriceDetailRoomPrice' should have a valid value!",
                    roomDetailsPriceDetailRoomPrice.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'roomDetailsPriceDetailRoomPrice' is null or empty. Failing test case!");
            assertThat("Field 'roomDetailsPriceDetailRoomPrice' is null or empty!", roomDetailsPriceDetailRoomPrice, notNullValue());
            assertThat("Field 'roomDetailsPriceDetailRoomPrice' should not be empty!", roomDetailsPriceDetailRoomPrice.toString(), not(isEmptyString()));
        }
        // Validate '@.room_details[0].price_detail.room_tax' field
        Object roomDetailsPriceDetailRoomTax = jsonPath.get("room_details[0].price_detail.room_tax");

        if (roomDetailsPriceDetailRoomTax != null && !roomDetailsPriceDetailRoomTax.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'roomDetailsPriceDetailRoomTax' has a valid value: " + roomDetailsPriceDetailRoomTax);
            assertThat("Field 'roomDetailsPriceDetailRoomTax' should have a valid value!",
                    roomDetailsPriceDetailRoomTax.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'roomDetailsPriceDetailRoomTax' is null or empty. Failing test case!");
            assertThat("Field 'roomDetailsPriceDetailRoomTax' is null or empty!", roomDetailsPriceDetailRoomTax, notNullValue());
            assertThat("Field 'roomDetailsPriceDetailRoomTax' should not be empty!", roomDetailsPriceDetailRoomTax.toString(), not(isEmptyString()));
        }

        // Validate 'room_details[0].price_detail.total' field
        Object roomDetailsPriceDetailtotal = jsonPath.get("room_details[0].price_detail.total");

        if (roomDetailsPriceDetailtotal != null && !roomDetailsPriceDetailtotal.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'roomDetailsPriceDetailtotal' has a valid value: " + roomDetailsPriceDetailtotal);
            assertThat("Field 'roomDetailsPriceDetailtotal' should have a valid value!",
                    roomDetailsPriceDetailtotal.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'roomDetailsPriceDetailtotal' is null or empty. Failing test case!");
            assertThat("Field 'roomDetailsPriceDetailtotal' is null or empty!", roomDetailsPriceDetailtotal, notNullValue());
            assertThat("Field 'roomDetailsPriceDetailtotal' should not be empty!", roomDetailsPriceDetailtotal.toString(), not(isEmptyString()));
        }
        // Validate '@.room_details[0].price_detail.price_breakup[0].price' field
        Object roomDetailsPriceDetailPriceBreakupPrice = jsonPath.get("room_details[0].price_detail.price_breakup[0].price");

        if (roomDetailsPriceDetailPriceBreakupPrice != null && !roomDetailsPriceDetailPriceBreakupPrice.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'roomDetailsPriceDetailPriceBreakupPrice' has a valid value: " + roomDetailsPriceDetailPriceBreakupPrice);
            assertThat("Field 'roomDetailsPriceDetailPriceBreakupPrice' should have a valid value!",
                    roomDetailsPriceDetailPriceBreakupPrice.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'roomDetailsPriceDetailPriceBreakupPrice' is null or empty. Failing test case!");
            assertThat("Field 'roomDetailsPriceDetailPriceBreakupPrice' is null or empty!", roomDetailsPriceDetailPriceBreakupPrice, notNullValue());
            assertThat("Field 'roomDetailsPriceDetailPriceBreakupPrice' should not be empty!", roomDetailsPriceDetailPriceBreakupPrice.toString(), not(isEmptyString()));
        }

        // Validate '@.room_details[0].price_detail.price_breakup[0].original_price' field
        Object roomDetailsPriceDetailPriceBreakupOriginalPrice = jsonPath.get("room_details[0].price_detail.price_breakup[0].original_price");

        if (roomDetailsPriceDetailPriceBreakupOriginalPrice != null && !roomDetailsPriceDetailPriceBreakupOriginalPrice.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'roomDetailsPriceDetailPriceBreakupOriginalPrice' has a valid value: " + roomDetailsPriceDetailPriceBreakupOriginalPrice);
            assertThat("Field 'roomDetailsPriceDetailPriceBreakupOriginalPrice' should have a valid value!",
                    roomDetailsPriceDetailPriceBreakupOriginalPrice.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'roomDetailsPriceDetailPriceBreakupOriginalPrice' is null or empty. Failing test case!");
            assertThat("Field 'roomDetailsPriceDetailPriceBreakupOriginalPrice' is null or empty!", roomDetailsPriceDetailPriceBreakupOriginalPrice, notNullValue());
            assertThat("Field 'roomDetailsPriceDetailPriceBreakupOriginalPrice' should not be empty!", roomDetailsPriceDetailPriceBreakupOriginalPrice.toString(), not(isEmptyString()));
        }

        // Validate '@.room_details[0].price_detail.price_breakup[0].tax_amount' field
        Object roomDetailsPriceDetailPriceBreakupTaxAmount = jsonPath.get("room_details[0].price_detail.price_breakup[0].tax_amount");

        if (roomDetailsPriceDetailPriceBreakupTaxAmount != null && !roomDetailsPriceDetailPriceBreakupTaxAmount.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'roomDetailsPriceDetailPriceBreakupTaxAmount' has a valid value: " + roomDetailsPriceDetailPriceBreakupTaxAmount);
            assertThat("Field 'roomDetailsPriceDetailPriceBreakupTaxAmount' should have a valid value!",
                    roomDetailsPriceDetailPriceBreakupTaxAmount.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'roomDetailsPriceDetailPriceBreakupTaxAmount' is null or empty. Failing test case!");
            assertThat("Field 'roomDetailsPriceDetailPriceBreakupTaxAmount' is null or empty!", roomDetailsPriceDetailPriceBreakupTaxAmount, notNullValue());
            assertThat("Field 'roomDetailsPriceDetailPriceBreakupTaxAmount' should not be empty!", roomDetailsPriceDetailPriceBreakupTaxAmount.toString(), not(isEmptyString()));
        }
    }

}
