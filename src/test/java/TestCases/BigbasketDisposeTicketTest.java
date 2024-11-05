package TestCases;

import Generic.BBBaseClass;
import Generic.BigbasketRoutes;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BigbasketDisposeTicketTest extends BBBaseClass {

    @Test
    public void DisposeTicketTest() throws IOException {

        Response response = RestAssured.given()
                .formParam("task_id", "571613948")
                .formParam("ticket_id", "6728393275244")
                .formParam("dispose_type", "C")
                .formParam("dispose_folder", "1202857")
                .formParam("dispose_parent_folder", "1202853")
                .formParam("resolve_remark", "test QA")
                .formParam("resolve_substatus", "RS")
                .formParam("order_id", "183442687")
                .formParam("customer_id", " 183442687")
                .formParam("pause_after_dispose", "0")
                .formParam("city", "Bangalore")
                .formParam("hub", "BNBLR-BN-Pura-GS8 ")
                .formParam("order_type", "bb####null####")
                .formParam("sub_status", "RS")
                .formParam("data_json", " {\"linked_orders\":null,\"fulfillment\":\"\",\"invoice\":null,\"order-type\":\"Normal\",\"validated\":\"No\",\"id\":1460981599,\"slot\":\"2024-05-17 12:00 AM to 11:00 PM\",\"city\":\"Bangalore\",\"item-nos\":1,\"external_order_id\":null,\"email\":\"shankarlamani8@gmail.com\",\"channel\":\"bb-android-app\",\"status\":\"cancelled\",\"source_slug\":\"bbnow\",\"sa_id\":10076,\"hub\":\"BNBLR-BN-Pura-GS8 \",\"po_id\":1531425278,\"order_tags\":[\"express\",\"first\",\"first_bbnow\",\"default_dm_info_due_to_config\"],\"admin-comment\":\"29/10 11:44 - dheeraj.kohale@bigbasket.com - #CB# Chat Ticket: 6728393275244\\n\\nCX concern was regarding  test call \\nout call to cx 8861872771 cx said that they are testing the issue this is test call so closed it \\n\\nhence case closed\\n\\n#CASE CLOSED# \\n/////////////////////////////////////////////////////////////////////////////////////////////////////////////// <br> 22/10 18:08 - roshni.sa@bigbasket.com - 1460981599\\nCustomer Support|Query|Incomplete Interaction|Incomplete Interaction|Abrupt Disconnection <br> 17/10 17:35 - ko.tejaswini@bigbasket.com - 6728996135840\\nticket went call team.  <br> 15/10 18:13 - syedjalaal.s@bigbasket.com - CT\\n <br> 10/10 18:52 - vaishali.dave@bigbasket.com - \\n6728393275244\\nchat assisged to call team <br> 09/10 15:36 - varsha.b2@bigbasket.com - 1460981599\\ncx called and dropped the call <br> 09/10 15:25 - pavithra.p@bigbasket.com - chat went to call  <br> 08/10 18:52 - dilipkumar.s7@bigbasket.com - 1460981599\\ncx called and said please stay on line and dropped the call.. <br> 08/10 18:46 - vinuta.halgeri@bigbasket.com - 6728393275244 \\n1460981599\\n#BBNOW#\\nChat assigned to call team <br> 04/10 10:19 - krishnaveni.kv@bigbasket.com - 6728017060467\\n1460981599\\n#bbnow\\nvoc Pls stay in line hence probed How may I help you today? but cx offline\\n\\n\\n\\n <br> 26/09 18:46 - divya.u@bigbasket.com - 1460981599\\ncx said stay on line and dropped call  <br> 26/09 18:33 - akash.gs@bigbasket.com - #ticket is assigned to call team <br> 23/09 16:19 - kasthuri.sunilkumar@bigbasket.com - 6727088530041\\nchat went to callteam <br> 19/09 18:36 - mahesh.m3@bigbasket.com - 1460981599 CX told to stay on line and dropped the call ticket not found to dispose <br> 17/09 16:24 - Ravi.a@bigbasket.com - chat moved to call team <br> 16/09 12:38 - chandani.gouda@bigbasket.com - \\n6725972911037\\n1460981599\\n#BBNOW\\nVOC: Blank chat, hence probed for the concern. cx offline.\\n <br> 16/09 12:04 - vidyashree.tp@bigbasket.com - 6725972911037\\n#1460981599\\n#BBNOW#\\nVOC: This conversation is marked as closed their is no concern hence probed for concern cx offline. (sep 13 chat) <br> 13/09 18:35 - sangeetha.g@bigbasket.com - 1460981599\\nas per cb  pinged SME as  there is no cmmts and as per previous records it was person from kapture and it was testing tickt hence clsoed the ticket  \\n(Call From :30226232502139606 - TEST - 13-09-2024) <br> 12/09 19:15 - mubarak.a2@bigbasket.com - 1460981599\\t\\nas per cb arranged there is no cmmts and as per previous records it was person from kapture and it was testing tickt hence clsoed the ticket NAT  <br> 12/09 18:31 - vivedha.r@bigbasket.com - chat moved to callback team\\n <br> 12/09 16:22 - ashwini.k@bigbasket.com - 1460981599\\nchat moved to clal team  <br> 10/09 18:34 - vodnala.saikumar@bigbasket.com - 1460981599\\ntest call and unable to tag tciket <br> 03/09 21:42 - rachana.nadakatti@bigbasket.com - chat moved to call team <br> 02/09 15:48 - sanjana.n3@bigbasket.com - 1460981599\\nchat have moved to call team. <br> 30/08 19:43 - nandhakumar.v@bigbasket.com - The chat session has been closed as the ticket is assigned to call team <br> 30/08 00:34 - anilkumar.ms@bigbasket.com - cx dropped  <br> 24/08 09:27 - deeksha.ks@bigbasket.com - 6722337966178\\ncx stated as blank chat, hence How may I assist you today?, cx offline.\\n\\n <br> 22/08 18:39 - Prince.Lazar@bigbasket.com - 6724331753955\\ncx cal droped <br> 19/08 19:34 - naveen.yj@bigbasket.com - Ticket No #719314497030\\nOrder Id--:--1460981599\\nTest Call Hence closed the Ticket\\n <br> 14/08 09:21 - sindhu.r@bigbasket.com - \\n6722337966178\\n1460981599\\n#bbnow#\\nBlank chat probed exact information looking for cx offline <br> 08/08 18:57 - lokesh.r5@bigbasket.com - 1460981599\\nChecked for the test call\\n\\n\\n <br> 06/08 18:49 - sindhu.k@bigbasket.com - 8861872771\\n1460981599\\ncx called but suddnely disconnected the call after probing how may assist you\\nticket is not refleting to dispose <br> 02/08 05:58 - v.jeevitha4@bigbasket.com - 721306165830\\nVOC blank chat hence probed How may I assist you today? CX offline. <br> 02/08 03:32 - nagraj.birajdar@bigbasket.com - Customer Support\\nQuery\\nOthers\\nOthers\\nTest Call <br> 01/08 18:54 - priya.rathore@bigbasket.com - 1460981599\\nkapture ticket not found, updated in admin <br> 01/08 18:53 - priya.rathore@bigbasket.com - 1460981599\\ncx tried to tell his concern but the concern was not clear to me info the same to cx cx dropped the call. <br> 31/07 12:02 - abhilash.m4@bigbasket.com - chat moved to call team. <br> 30/07 18:05 - sharulatha.l@bigbasket.com - 6722337966178 CHAT ASSIGNED TO CALL SESSION.\\n <br> 30/07 18:03 - rohan.vn@bigbasket.com - 1460981599\\nTest call\\nticket not found <br> 24/07 18:46 - konigapogu.vv@bigbasket.com - 1460981599\\nNo Ticket Found to Dispose. <br> 23/07 18:13 - chandana.s@bigbasket.com - Chat went to call team.\\n <br> 23/07 17:35 - preethi.g@bigbasket.com - 1460981599  no ticket to dispose the call... <br> 23/07 17:27 - preethi.g@bigbasket.com - 1460981599  cx as called asking for ticket id  from cs,,, because cx have multiple  ticket id... later cx told cx got a ticket id suddenly call got dropped... <br> 18/07 18:31 - sufiyan.c@bigbasket.com - chat moved to call team  <br> 16/07 18:29 - afra.begum@bigbasket.com - 1460981599\\nCustomer Support\\nQuery\\nOthers\\nOthers\\nTest Call <br> 16/07 17:12 - akshata.turkani@bigbasket.com - CHAT HAS BEEN AUTO DISPOSED AND ASSIGNED TO CALL TEAM.\\n <br> 15/07 17:04 - bhavanashree.ks@bigbasket.com - chat moved to call team <br> 14/07 13:56 - yuvaraj.g@bigbasket.com - Ticket No #719314497030 #1460981599 #CSAT #BB #MI Called, the person said he is from kapture team, its basically for testing purpose, please ignore it, hence closed. <br> 11/07 16:49 - sudhindraprakash.kulkarni@bigbasket.com - 720521176911\\n\\nCHAT MOVED TO CALL TEAM\\n <br> 11/07 13:25 - pendhem.satish@bigbasket.com - 719921560171\\n1460981599\\n#BBNOW#\\ncx stated Test Me know hence i probed for I request you to help me with the information you are looking for. cx no response <br> 11/07 10:47 - syedjalaal.s@bigbasket.com - 720521176911\\n1460981599\\nBBNOW#\\nI request you to help me with the exact product issue in order to assist you further.\\n\\n\\n <br> 11/07 10:17 - mohammed.afan@bigbasket.com - 720521176911\\n1460981599\\n#BBNOW\\nvoc shared ss probed for issue with the product offline chat <br> 04/07 16:02 - g.poojitha@bigbasket.com - assigned to CT <br> 27/06 18:14 - umme.salma@bigbasket.com - 719314497030\\n1460981599\\n#BBNOW#\\ncx state blank chat Hence probed  kindly let me know what is the information you are looking for? cx offline. <br> 27/06 18:07 - chandana.s@bigbasket.com - Chat went to call team.\\n <br> 25/06 16:57 - dhanalakshmi.mb@bigbasket.com - Chat assigned to call team\\n <br> 13/06 16:47 - valmikithilak.kumar@bigbasket.com - chat moved to call team <br> 11/06 17:21 - ahamadi.f@bigbasket.com - Chat moved to call team\\n <br> 03/06 12:30 - suguna.km@bigbasket.com - 716307825833\\n1460981599\\n#bbnow\\nblank chat probed for concern. <br> 27/05 17:09 - shob.raj@bigbasket.com - 716307825833\\nNO response from cx hence How may I help you today?\\n <br> 17/05 09:10 - System - Cancellation Type: Order cancelled because payment gets failed Comment: Order cancelled because payment gets failed\",\"dc\":null,\"delivery\":\"2024-05-17\",\"phone\":\"8861872771\",\"dm_id\":2,\"address\":\"v s pg for gents 3rd cross RHB colony 1 main\",\"payment\":\"cod\",\"full-id\":\"BNN-1460981599-20240517\",\"name\":\"s l\",\"created\":\"2024-05-17 08:55 AM\",\"slot-changes\":0,\"invoice-total\":\"25.0\",\"mapped\":\"No\",\"source_id\":10,\"lastconversation_type\":\"D\",\"ticketId\":\"6728393275244\"}")
                .formParam("new_ticket_ui", "true")
                .formParam("ticket_summary_id", "bdb19c7d-e1ff-4728-b0cd-1998f2e526d3")
                .formParam("", "bb####null####")
                .formParam("sub_status", "RS")

                .cookies(BBBaseClass.cookies)
                .post(BigbasketRoutes.ticketDispose);

        response.then().log().all();

        int statusCode = response.statusCode();
        System.out.println("status code " + statusCode);
        response.then().statusCode(200);

        //Response time validation
        long responseTime = response.getTime();
        System.out.println("Response Time =" + responseTime);
        assertTrue(responseTime < 3000, "Response time exceeds the acceptable threshold of 3000 milliseconds");

        // Parameter validation
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);

        Object ForResponseParametersValidation = jsonPath.get("status");
        assertEquals(String.valueOf(ForResponseParametersValidation), "Success");
        System.out.println("******************************");
        System.out.println(ForResponseParametersValidation);
    }
}
