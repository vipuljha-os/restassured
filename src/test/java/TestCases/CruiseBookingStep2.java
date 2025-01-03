package TestCases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class CruiseBookingStep2 {
    private static final String URL = "https://bahamas.kapturecrm.com/itinerary-wise-sailing-details";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 18000;

    @Test
    public void VerifyCruiseBookingStep2() {
        // Request body
        String requestBody = "{\n" +
                "    \"itinerary_name\": \"2-Night Bahamas\",\n" +
                "    \"year_month\": \"2025/09\"\n" +
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

        // Validate 'sessionId' field
        Object sessionId = jsonPath.get("session_id");

        if (sessionId != null && !sessionId.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'sessionId' has a valid value: " + sessionId);
            assertThat("Field 'sessionId' should have a valid value!",
                    sessionId.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'sessionId' is null or empty. Failing test case!");
            assertThat("Field 'sessionId' is null or empty!", sessionId, notNullValue());
            assertThat("Field 'sessionId' should not be empty!", sessionId.toString(), not(isEmptyString()));
        }
        // Validate 'sailingDetails' field
        Object sailingDetails = jsonPath.get("sailing_details");

        if (sailingDetails != null && !sailingDetails.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'sailingDetails' has a valid value: " + sailingDetails);
            assertThat("Field 'sailingDetails' should have a valid value!",
                    sailingDetails.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'sailingDetails' is null or empty. Failing test case!");
            assertThat("Field 'sailingDetails' is null or empty!", sailingDetails, notNullValue());
            assertThat("Field 'sailingDetails' should not be empty!", sailingDetails.toString(), not(isEmptyString()));
        }
        // Validate 'availableOfferArray' field
        Object availableOfferArray = jsonPath.get("available_offer_array");

        if (availableOfferArray != null && !availableOfferArray.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'availableOfferArray' has a valid value: " + availableOfferArray);
            assertThat("Field 'availableOfferArray' should have a valid value!",
                    sailingDetails.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'availableOfferArray' is null or empty. Failing test case!");
            assertThat("Field 'availableOfferArray' is null or empty!", availableOfferArray, notNullValue());
            assertThat("Field 'availableOfferArray' should not be empty!", availableOfferArray.toString(), not(isEmptyString()));
        }

        // Validate 'sailing_details[0].default_offer_id' field
        Object sailingDetailsDefaultOfferId = jsonPath.get("sailing_details[0].default_offer_id");

        if (sailingDetailsDefaultOfferId != null && !sailingDetailsDefaultOfferId.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'sailingDetailsDefaultOfferId' has a valid value: " + sailingDetailsDefaultOfferId);
            assertThat("Field 'sailingDetailsDefaultOfferId' should have a valid value!",
                    sailingDetailsDefaultOfferId.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'sailingDetailsDefaultOfferId' is null or empty. Failing test case!");
            assertThat("Field 'sailingDetailsDefaultOfferId' is null or empty!", sailingDetailsDefaultOfferId, notNullValue());
            assertThat("Field 'sailingDetailsDefaultOfferId' should not be empty!", sailingDetailsDefaultOfferId.toString(), not(isEmptyString()));
        }

        // Validate '@.sailing_details[0].categories[0].original_price' field
        Object sailingDetailsCategoriesOriginalPrice = jsonPath.get("sailing_details[0].categories[0].original_price");

        if (sailingDetailsCategoriesOriginalPrice != null && !sailingDetailsCategoriesOriginalPrice.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'sailingDetailsCategoriesOriginalPrice' has a valid value: " + sailingDetailsCategoriesOriginalPrice);
            assertThat("Field 'sailingDetailsDefaultOfferId' should have a valid value!",
                    sailingDetailsCategoriesOriginalPrice.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'sailingDetailsCategoriesOriginalPrice' is null or empty. Failing test case!");
            assertThat("Field 'sailingDetailsCategoriesOriginalPrice' is null or empty!", sailingDetailsCategoriesOriginalPrice, notNullValue());
            assertThat("Field 'sailingDetailsCategoriesOriginalPrice' should not be empty!", sailingDetailsCategoriesOriginalPrice.toString(), not(isEmptyString()));
        }

        // Validate '@.sailing_details[0].available_offer_ids' field
        Object sailingDetailsAvailableOfferIds = jsonPath.get("sailing_details[0].available_offer_ids");

        if (sailingDetailsAvailableOfferIds != null && !sailingDetailsAvailableOfferIds.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'sailingDetailsAvailableOfferIds' has a valid value: " + sailingDetailsAvailableOfferIds);
            assertThat("Field 'sailingDetailsAvailableOfferIds' should have a valid value!",
                    sailingDetailsAvailableOfferIds.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'sailingDetailsAvailableOfferIds' is null or empty. Failing test case!");
            assertThat("Field 'sailingDetailsAvailableOfferIds' is null or empty!", sailingDetailsAvailableOfferIds, notNullValue());
            assertThat("Field 'sailingDetailsAvailableOfferIds' should not be empty!", sailingDetailsAvailableOfferIds.toString(), not(isEmptyString()));
        }

    }
}
