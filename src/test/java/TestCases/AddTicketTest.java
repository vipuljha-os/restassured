package TestCases;

import FileUtility.FileLib;
import Generic.MeeshoSXLogin;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Generic.Routes.addTicket;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class AddTicketTest extends MeeshoSXLogin {

    @Test
    public void AddTicket() {
        hardWait();
        long start = System.currentTimeMillis();
        System.out.println(start);
        System.out.println(cookies.toString());
        Response response = given()
                .cookies(cookies)
                //.formParams(map)
                .formParam("customer_id", "130754919")
                .formParam("enquiry_id", "0")
                .formParam("contact_id", "152105038")
                .formParam("customer_name", "Kapture QA ")
                .formParam("customer_email", "Shankar.lamani@kapturecrm.com")
                .formParam("customer_phone", "8861872771")
                .formParam("folder_id", "1084499")
                .formParam("associate_field_data", "obj2058_attr11528=111&obj2057_attr11481=04%2F14%2F2024&obj2058_attr11529=1&obj2057_attr11480=02-Home_Repair&obj2057_attr11483=Local-Workshop%2FField&obj1929_attr13960=End+Customer&obj2057_attr11487=FGSNO1&quantity=1&obj1928_attr10588=02-Home_Repair&obj1928_attr10584=FGSNO1&obj2057_attr11523=000000000006660870&obj1928_attr10585=000000000006660870&obj1928_attr10608=2IN1+AIR+PURI-HUMI+COMBI+SERIE&obj2057_attr11482=TETS&obj2056_attr11468=Kapture+QA+&obj2056_attr11469=Reddy&obj2056_attr11471=vipul.jha%40kapture.com&obj2056_attr11470=7083923364&obj2056_attr11574=&obj2056_attr11573=H007&obj2056_attr11572=Daman+&obj2056_attr11477=Address%2C+Address+City&obj2056_attr11478=&obj2056_attr11472=India&obj2056_attr11473=Uttar+Pradesh&obj2056_attr11474=Lucknow&obj2056_attr11475=226002&same_as_sold_to_party=&obj2055_attr11455=Kapture+QA+&obj2055_attr11456=Reddy&obj2055_attr11458=vipul.jha%40kapture.com&obj2055_attr11457=7083923364&obj2055_attr11576=&obj2055_attr11575=H007&obj2055_attr11466=Daman+&obj2055_attr11464=Address%2C+Address+City&obj2055_attr11465=&obj2055_attr11459=India&obj2055_attr11460=Uttar+Pradesh&obj2055_attr11461=Lucknow&obj2055_attr11462=226002&obj1929_attr10611=Kapture+QA+&obj1929_attr10612=7083923364&obj1929_attr10623=vipul.jha%40kapture.com&obj1929_attr10615=India&obj1929_attr10616=Uttar+Pradesh&obj1929_attr10617=Lucknow&obj1929_attr10614=226002&obj1929_attr10619=&obj1929_attr10622=Address%2C+Address+City&obj1929_attr10613=&obj1928_attr10594=Philips-Staging&obj2058_attr11888=Philips-Staging&obj2058_attr12305=Brigade+metropolis.+Bangalore&obj2058_attr11890=162581&obj2052_attr11426=Yes&obj2058_attr14153=9088989833&undefined=0")
                .formParam("selected_folder_list", "1084499")
                .formParam("customer_code", "")
                .formParam("is_folder_mandatory", "")
                .formParam("is_folder_enable_for_assign", "")
                .formParam("taskTitle", "")
                .formParam("next_follow_up", "")
                .formParam("priority", "")
                .formParam("assign_emp", "162581#Philips-Staging")
                .formParam("tkt_attachment", "")
                .formParam("files", "")
                .formParam("redirect_to_ticket_detail", "no")
                .formParam("customer_last_name", "Lamani")
                .formParam("customer_alternative_phone", "")
                .formParam("customer_house", "H007")
                .formParam("customer_street", "Daman")
                .formParam("customer_pincode", "226002")
                .formParam("customer_address", "Address, Address City")
                .formParam("customer_address2", "")
                .formParam("customer_locality", "Krishnarajapuram")
                .formParam("customer_city", "Lucknow")
                .formParam("customer_state", "Uttar Pradesh")
                .formParam("customer_country", "India")
                .formParam("product_ids", "1063245")
                .formParam("sku_code", "000000000006660870")
                .formParam("sub_status", "OC")
                .formParam("assignee_queue", "IN10")
                .formParam("is_send_otp", "1")
                .when()
                .post(addTicket);

        long end = System.currentTimeMillis();
        System.out.println(end);
        System.out.println("Time taken is " + (end-start));

        //Status code validation
        int statusCode = response.getStatusCode();
        System.out.println("status code " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time =" + responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        //Parameter validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);
        Object ForResponseParametersValidation = jsonPath.get("response.statusName");
        assertThat(ForResponseParametersValidation, equalTo("Pending"));
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);

        //To Store the Created ticket Id
        String createdTicketId = jsonPath.get("response.ticketId");
        System.out.println("***********************");
        System.out.println(createdTicketId);
        FileLib.writeDataIntoPropertyFile(createdTicketId);

    }
}
