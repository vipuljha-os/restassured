package TestCases;

import FileUtility.FileLibOne;
import Generic.ZeptoLogin;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Generic.ZeptoRoutes.addTicket;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class ZeptoAddTicketTest extends ZeptoLogin {
    public String ticketId;
    public String taskId;

    @Test
    void addTicket() {

        Response response = given()
                .cookies(cookies)
                //.formParams(map)
                .formParam("customer_id", "206153489")
                .formParam("contact_id", "224574114")
                .formParam("customer_name", "Mansi Amrutwar")
                .formParam("customer_email", "")
                .formParam("customer_phone", "7083223291")
                .formParam("folder_id", "634222")
                .formParam("associate_field_data", "obj2349_attr13342=&obj2376_attr13585=&obj2376_attr20569=&obj2376_attr20812=&obj2376_attr21685=&obj2892_attr17481=&obj2892_attr17482=&obj2892_attr17483=&obj2892_attr17484=&obj2892_attr17485=&obj2892_attr17667=")
                .formParam("selected_folder_list", "634217,634218,634222")
                .formParam("customer_code", "7083223291")
                .formParam("is_folder_mandatory", "")
                .formParam("is_folder_enable_for_assign", "")
                .formParam("taskTitle", "")
                .formParam("next_follow_up", "")
                .formParam("priority", "")
                .formParam("assign_emp", "148232#Zepto SUPERADMIN")
                .formParam("tkt_attachment", "")
                .formParam("files", "")
                .formParam("sub_status", "")
                .formParam("redirect_to_ticket_detail", "no")
                .formParam("external_order_detail", "null")
                .formParam("pending_task_id", "false")
                .formParam("taskDetail", "test")
                .when()
                .post(addTicket);
        response.then().log().all();

        ticketId = response.jsonPath().getString("response.ticketId");
        taskId = response.jsonPath().getString("response.id");

        //Status code validation
        int statusCode = response.getStatusCode();
        System.out.println("status code " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time =" + responseTime);
        assertTrue(responseTime < 10000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Parameter validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);
        Object ForResponseParametersValidation = jsonPath.get("response.statusName");
        assertThat(ForResponseParametersValidation, equalTo("Pending"));
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);
        System.out.println("Ticket has been added successfully with Ticket_Id : " + ticketId);
        FileLibOne.writeDataIntoPropertyFile(ticketId, taskId);

    }
}
