package TestCases;

import Generic.MeeshoSXLogin;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Generic.Routes.reopenTicket;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ReopenTicketTest extends MeeshoSXLogin {

    @Test
    public void ReopenTicket() {
        hardWait();
        // Set headers
        Response response = given()
                .accept("application/json, text/plain, */*")
                .header("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8")
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .cookies(cookies)
               // .cookie("_ga=GA1.1.738408862.1713186231; ext_name=ojplmecpdpgccookcobabopnaifgidhf; timeOffset=0; _KAPTURECRM_SESSION_AUTH_ADMIN=alRKNmdOeGdaTkhtZDlnckhobFh3dz09|Wnc5Q3NQUE1MckNzcWE1L3NCalBWZz09; _KAPTURECRM_SESSION=kukn95szivebk4lm4nvu; av=uilt; PHPSESSID=cjj8bhm8804ms2lullgj98etfb; mp_6c02537f8f758b0feedfd53581fbaeec_mixpanel=%7B%22distinct_id%22%3A%20%22%24device%3A18ee1db50f72c9-0fa0edb929e7ff-26001a51-e1000-18ee1db50f72ca%22%2C%22%24device_id%22%3A%20%2218ee1db50f72c9-0fa0edb929e7ff-26001a51-e1000-18ee1db50f72ca%22%2C%22%24initial_referrer%22%3A%20%22%24direct%22%2C%22%24initial_referring_domain%22%3A%20%22%24direct%22%2C%22__mps%22%3A%20%7B%7D%2C%22__mpso%22%3A%20%7B%22%24initial_referrer%22%3A%20%22%24direct%22%2C%22%24initial_referring_domain%22%3A%20%22%24direct%22%7D%2C%22__mpus%22%3A%20%7B%7D%2C%22__mpa%22%3A%20%7B%7D%2C%22__mpu%22%3A%20%7B%7D%2C%22__mpr%22%3A%20%5B%5D%2C%22__mpap%22%3A%20%5B%5D%7D; session_expire=eyJzeXN0ZW1UaW1lIjoxNzEzNTA0NjU2OTcyLCJleHBpcnlUaW1lIjoxNzEzNTQwNjU2OTcyfQ==; JSESSIONID=B0AC4195CB35E5EE59D6F91FC0C13724; _ga_KKZH5KWGEE=GS1.1.1713504101.22.1.1713504675.17.0.0; JSESSIONID=186B0771638A0ED45AE064157DC9D965")
                .formParam("task_ids", "528961224")
                .formParam("task_detail", "TEST")
                .formParam("sub_status", "RE")
                .formParam("data_json", "[{\"id\":528961224,\"title\":\"I have received damaged return\",\"ticket-id\":\"8713333244222\",\"assign-id\":0,\"status\":\"C\",\"task-type\":\"O\",\"last-con-type\":\"O\",\"last-con-id\":164892619,\"folder-id\":750893,\"substatus\":\"RS\",\"sla\":0,\"reference-id\":129115675,\"contact-id\":146726226,\"creator-id\":0,\"order-id\":\"760600082958_1\",\"cDate\":\"04/17/2024 11:24:04\",\"next-follow-up\":\"04/19/2024 17:24:00\",\"priority\":2,\"last-con-time\":1713333244225,\"total-con-count\":1,\"detail\":\" - TEST - TEST - TEST - test - TEST - test - TEST - Test - Incorrect Delivery - Test - Test - TEST - Test - TEST - Test - Test - Test - Test - Test - Test - Test - Test - Test - Test - Test - Correct Pickup - TEST - \",\"ticket-email\":\"sameTestmail@meesho.com##9955326756\",\"queue\":\"\",\"task-end\":\"04/19/2024 11:00:49\",\"cname\":\"Sanjeev\",\"ccode\":\"1\",\"email\":\"sameTestmail@meesho.com\",\"phone\":\"9955326756\",\"enquiry-id\":0,\"last-follow-up\":\"04/19/2024 11:00:48\",\"attachments\":\"\",\"mergeTicketId\":\"\"}]")
                .when()
                .post(reopenTicket);
        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time ="+ responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Parameter validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);

        Object ForResponseParametersValidation = jsonPath.get("status");
        assertEquals(String.valueOf(ForResponseParametersValidation), "Success");
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);
    }
}
