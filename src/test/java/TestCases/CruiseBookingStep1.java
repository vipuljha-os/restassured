package TestCases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class CruiseBookingStep1 {
    private static final String URL = "https://bahamas.kapturecrm.com/get-cruise-itinerary-availability";
    private static final String AUTHORIZATION_HEADER = "Basic YmFoYW1hc3BhcmFkaXNlOkFXNTREMk9RRzBGOFo4";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_RESPONSE_TIME = 15000;

    @Test
    public void VerifyCruiseBookingStep1Test() {
        // Request body
        String requestBody = "{\n" +
                "    \"itinerary_name\":\"2-Night Bahamas\",\n" +
                "    \"year_month\":\"2025/09\"\n" +
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
        System.out.println("****************");
        System.out.println(response);
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
        Object ForResponseParametersValidation_Message = jsonPath.get("message");
        assertThat(ForResponseParametersValidation_Message, equalTo("sailing-details found successfully"));
        System.out.println("******************************");

        // Validate 'itineraryName' field
        Object itineraryName = jsonPath.get("available_itineries_details[0].itinerary_name\n");

        if (itineraryName != null && !itineraryName.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'itineraryName' has a valid value: " + itineraryName);
            assertThat("Field 'itineraryName' should have a valid value!",
                    itineraryName.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'itineraryName' is null or empty. Failing test case!");
            assertThat("Field 'itineraryName' is null or empty!", itineraryName, notNullValue());
            assertThat("Field 'itineraryName' should not be empty!", itineraryName.toString(), not(isEmptyString()));
        }
        // Validate 'cruiseName' field
        Object cruiseName = jsonPath.get("available_itineries_details[0].cruise_name");

        if (cruiseName != null && !cruiseName.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'cruiseName' has a valid value: " + cruiseName);
            assertThat("Field 'cruiseName' should have a valid value!",
                    itineraryName.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'cruiseName' is null or empty. Failing test case!");
            assertThat("Field 'cruiseName' is null or empty!", cruiseName, notNullValue());
            assertThat("Field 'cruiseName' should not be empty!", cruiseName.toString(), not(isEmptyString()));
        }
        // Validate 'available_itineries_details' field
        Object availableItineriesDetails = jsonPath.get("available_itineries_details");

        if (availableItineriesDetails != null && !availableItineriesDetails.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'availableItineriesDetails' has a valid value: " + availableItineriesDetails);
            assertThat("Field 'availableItineriesDetails' should have a valid value!",
                    itineraryName.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'availableItineriesDetails' is null or empty. Failing test case!");
            assertThat("Field 'availableItineriesDetails' is null or empty!", availableItineriesDetails, notNullValue());
            assertThat("Field 'availableItineriesDetails' should not be empty!", availableItineriesDetails.toString(), not(isEmptyString()));
        }

        // Validate 'available_itineries_details[0].itinerary_id' field
        Object availableItineriesDetailsItineraryID = jsonPath.get("available_itineries_details[0].itinerary_id");

        if (availableItineriesDetailsItineraryID != null && !availableItineriesDetailsItineraryID.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'availableItineriesDetailsItineraryID' has a valid value: " + availableItineriesDetailsItineraryID);
            assertThat("Field 'availableItineriesDetailsItineraryID' should have a valid value!",
                    availableItineriesDetailsItineraryID.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'availableItineriesDetailsItineraryID' is null or empty. Failing test case!");
            assertThat("Field 'availableItineriesDetailsItineraryID' is null or empty!", availableItineriesDetailsItineraryID, notNullValue());
            assertThat("Field 'availableItineriesDetailsItineraryID' should not be empty!", availableItineriesDetailsItineraryID.toString(), not(isEmptyString()));
        }

        // Validate 'available_itineries_details[0].offers' field
        Object availableItineriesDetailsOffers = jsonPath.get("available_itineries_details[0].offers");

        if (availableItineriesDetailsOffers != null && !availableItineriesDetailsOffers.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'availableItineriesDetailsOffers' has a valid value: " + availableItineriesDetailsOffers);
            assertThat("Field 'availableItineriesDetailsOffers' should have a valid value!",
                    availableItineriesDetailsOffers.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'availableItineriesDetailsOffers' is null or empty. Failing test case!");
            assertThat("Field 'availableItineriesDetailsOffers' is null or empty!", availableItineriesDetailsOffers, notNullValue());
            assertThat("Field 'availableItineriesDetailsOffers' should not be empty!", availableItineriesDetailsOffers.toString(), not(isEmptyString()));
        }

        // Validate '@.available_itineries_details[1].itinerary_id' field
        Object availableItineriesDetailsItineraryId = jsonPath.get("available_itineries_details[1].itinerary_id");

        if (availableItineriesDetailsItineraryId != null && !availableItineriesDetailsOffers.toString().isEmpty()) {
            // Value is not null or empty
            System.out.println("Field 'availableItineriesDetailsItineraryId' has a valid value: " + availableItineriesDetailsItineraryId);
            assertThat("Field 'availableItineriesDetailsItineraryId' should have a valid value!",
                    availableItineriesDetailsItineraryId.toString().trim(), not(isEmptyOrNullString()));
        } else {
            // Value is null or empty
            System.out.println("Field 'availableItineriesDetailsItineraryId' is null or empty. Failing test case!");
            assertThat("Field 'availableItineriesDetailsItineraryId' is null or empty!", availableItineriesDetailsItineraryId, notNullValue());
            assertThat("Field 'availableItineriesDetailsItineraryId' should not be empty!", availableItineriesDetailsItineraryId.toString(), not(isEmptyString()));
        }
    }
}

