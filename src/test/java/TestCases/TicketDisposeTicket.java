package TestCases;

import FileUtility.FileLib;
import Generic.MeeshoSXLogin;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static Generic.Routes.ticketDispose;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TicketDisposeTicket extends MeeshoSXLogin {
    @Test
    public void disposeTicket() {
            Response response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .formParam("task_id", "528961224")
                    .formParam("ticket_id","8713333244222")
                    .formParam("associate_field_data", "obj1971_attr11237=760600082958_1%2C859356362480_1%2C979990201778_1%2C790304646458_1%2C422529826142_1%2C769123915441_1%2C998695618268_1%2C370304333690_1%2C774569393231_1%2C560270367127_1%2C774572528158_1%2C603631433150_1%2C892073389090_1%2C483469455944_1%2C947431340915_1%2C971246463579_1%2C188091925044_1%2C761524604893_1&obj1971_attr12553=&obj1971_attr12554=I+have+received+damaged+return&obj1971_attr12557=&obj2242_attr12604=other&obj2242_attr12605=&obj2242_attr12606=&obj2242_attr12607=NA&obj2242_attr12608=intact&obj2242_attr12609=56565656&obj2242_attr12610=&obj2242_attr12611=NA&obj2242_attr12612=yes&obj2242_attr12613=No&obj2242_attr12615=&obj2242_attr12616=BELOW_THRESHOLD&obj2242_attr12617=Below+1K&obj2242_attr12618=&obj2242_attr12619=&obj2242_attr12620=&obj2242_attr12621=&obj2242_attr12622=meesho_supplier_panel&obj2242_attr12623=&obj2242_attr14578=&obj2242_attr15246=&obj2242_attr15839=&obj2246_attr12633=null&obj2246_attr12634=null&obj2246_attr12635=&obj2246_attr16252=&obj2246_attr16823=&obj2246_attr17003=&obj2324_attr13247=&obj2324_attr13248=&obj2526_attr14723=&obj2526_attr14724=&obj2578_attr15007=&obj2644_attr16132=&obj2644_attr16144=&obj2644_attr20059=&obj2740_attr16195=&obj2830_attr17079=&obj2955_attr17911=&obj2955_attr17912=&obj2955_attr17913=&obj2973_attr18084=&obj2973_attr18085=&obj2973_attr18086=")
                    .formParam("selected_folder_list","734905,750885,750893")
                    .formParam("sub_status", "RS")
                    .formParam("update_folder_id","750893")
                    .formParam("pause_after_disposition", "0")
                    .formParam("pause_reason_for_disposition","")
                    .formParam("data_json", "[{\"id\":528961224,\"title\":\"I have received damaged return\",\"ticket-id\":\"8713333244222\",\"assign-id\":0,\"status\":\"P\",\"task-type\":\"O\",\"last-con-type\":\"O\",\"last-con-id\":164892619,\"folder-id\":734905,\"substatus\":\"US\",\"sla\":0,\"reference-id\":129115675,\"contact-id\":146726226,\"creator-id\":0,\"order-id\":\"760600082958_1\",\"cDate\":\"04/17/2024 11:24:04\",\"next-follow-up\":\"04/19/2024 17:24:00\",\"priority\":2,\"last-con-time\":1713333244225,\"total-con-count\":1,\"detail\":\"\",\"ticket-email\":\"sameTestmail@meesho.com##9955326756\",\"queue\":\"\",\"task-end\":\"04/17/2024 11:24:04\",\"cname\":\"Sanjeev\",\"ccode\":\"1\",\"email\":\"sameTestmail@meesho.com\",\"phone\":\"9955326756\",\"enquiry-id\":0,\"last-follow-up\":\"04/17/2024 11:24:04\",\"attachments\":\"\",\"mergeTicketId\":\"\"}]")
                    .formParam("ignore_alert","")
                    .formParam("is_close_chat_con", "")
                    .formParam("new_ticket_ui","true")
                    .formParam("task_detail","TEST")
                    .formParam("assign_to","156999#Meesho Sx (Test)")
                    .formParam("call_back_time","Invalid date")
                    .formParam("assignee_queue","Above_1K")
                    .formParam("assignee_queueNameTemp","Above 1K")
                    .formParam("order_id","760600082958_1")
                    .formParam("order_json","")
                    .cookies(cookies)
                    .post(ticketDispose);

        //Status code validation
        int statusCode = response.statusCode();
        System.out.println("status code " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Code ="+ responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Parameter validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);

        Object ForResponseParametersValidation = jsonPath.get("status");
        //assertEquals("null", String.valueOf(ForResponseParametersValidation));
        assertEquals(String.valueOf(ForResponseParametersValidation), "Success");
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);

    }
}