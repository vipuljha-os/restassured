package TestCases;

import FileUtility.FileLibOne;
import Generic.GoldenRamaLogin;
import Generic.GoldenRamaRoutes;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class GoldenRamaAddTicketTest extends GoldenRamaLogin {
    public String ticketId;
    public String taskId;
    @Test
    public void addTicketGoldenRama(){
        Response response = given()
                .cookies(cookies)
                //.formParams(map)
                .formParam("customer_id", "212127644")
                .formParam("enquiry_id","")
                .formParam("contact_id", "230536666")
                .formParam("customer_name", "moushumi test")
                .formParam("customer_code","")
                .formParam("customer_email", "tesst@gmail.com")
                .formParam("customer_phone", "9595959595976")
                .formParam("folder_id", "1522285")
                .formParam("associate_field_data", "")
                .formParam("selected_folder_list", "1522285")
                .formParam("is_folder_mandatory", "")
                .formParam("is_folder_enable_for_assign", "")
                .formParam("taskTitle", "Test")
                .formParam("next_follow_up", "")
                .formParam("priority", "")
//                .formParam("assign_emp", "179446#GOLDEN RAMA")
                .formParam("tkt_attachment", "")
                .formParam("files", "")
                .formParam("sub_status", "")
                .formParam("redirect_to_ticket_detail", "no")
                .formParam("external_order_detail", "null")
                .formParam("pending_task_id", "false")
                .formParam("taskDetail", "test")
                .when()
                .post(GoldenRamaRoutes.addTicket);
        response.then().log().all();

        ticketId = response.jsonPath().getString("response.ticketId");
        taskId = response.jsonPath().getString("response.id");

        //Status code validation
        int statusCode = response.getStatusCode();
        System.out.println("Status code => " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time => " + responseTime);
        assertTrue(responseTime < 10000, "Response time exceeds the acceptable threshold of 10000 milliseconds");

        //Parameter validation
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        Object ForResponseParametersValidation = jsonPath.get("response.statusName");
        assertThat(ForResponseParametersValidation, equalTo("Pending"));
        System.out.println("------------------------------");
        System.out.println("Ticket has been added successfully with Ticket_Id : " + ticketId);
        FileLibOne.writeDataIntoPropertyFileGoldenRama(ticketId, taskId);
    }
}
