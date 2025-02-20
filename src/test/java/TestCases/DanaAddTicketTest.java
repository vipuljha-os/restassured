package TestCases;

import FileUtility.FileLibOne;
import Generic.DanaLogin;

import Generic.DanaRoutes;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class DanaAddTicketTest extends DanaLogin {
    public String ticketIdDana;
    public String taskIdDana;
    @Test
    public void addTicketDana(){
            Response response = given()
                    .cookies(cookies)
                    //.formParams(map)
                    .formParam("customer_id", "214163334")
                    .formParam("enquiry_id","")
                    .formParam("contact_id", "232568629")
                    .formParam("customer_name", "Test")
                    .formParam("customer_code","")
                    .formParam("customer_email", "t96048509@gmail.com")
                    .formParam("customer_phone", "")
                    .formParam("folder_id", "2214690")
                    .formParam("associate_field_data", "")
                    .formParam("selected_folder_list", "2214277,2214278,2214670,2214688,2214690")
                    .formParam("is_folder_mandatory", "")
                    .formParam("is_folder_enable_for_assign", "")
                    .formParam("taskTitle", "Test NUI Template")
                    .formParam("next_follow_up", "")
                    .formParam("priority", "0")
                    .formParam("tkt_attachment", "")
                    .formParam("files", "")
                    .formParam("sub_status", "")
                    .formParam("redirect_to_ticket_detail", "no")
                    .formParam("external_order_detail", "null")
                    .formParam("pending_task_id", "false")
                    .formParam("taskDetail", "Testing 23465")
                    .formParam("uniqueFolderID","2214690")
                    .formParam("_templateId","36884")
                    .when()
                    .post(DanaRoutes.addTicket);
            response.then().log().all();

            ticketIdDana = response.jsonPath().getString("response.ticketId");
            taskIdDana = response.jsonPath().getString("response.id");

            //Status code validation
            int statusCode = response.getStatusCode();
            System.out.println("Status code => " + statusCode);
            response.then().statusCode(200);

            //Response time validation
            long responseTime = response.getTime();
            System.out.println("Response Time => " + responseTime);
            assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

            //Parameter validation
            String responseBody = response.getBody().asString();
            JsonPath jsonPath = new JsonPath(responseBody);
            Object ForResponseParametersValidation = jsonPath.get("response.statusName");
            assertThat(ForResponseParametersValidation, equalTo("Pending"));
            System.out.println("------------------------------");
            System.out.println("Ticket has been added successfully with Ticket_Id : " + ticketIdDana);
            FileLibOne.writeDataIntoPropertyFileDana(ticketIdDana, taskIdDana);
    }
}
