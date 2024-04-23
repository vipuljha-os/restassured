package Generic;

public class Routes {
    public static String BaseURL= "https://meeshosx.kapturecrm.com";

    //user module
    public static String login= BaseURL+"/ms/auth/mobile-authenticate-user/";
    public static String addTicket= BaseURL+"/api/version3/ticket/add-ticket";
    public static String GetTicketList= BaseURL+"/api/version3/ticket/get-ticket-list";
    public static String cannedResponse= BaseURL+"/api/version3/ticket/get-social-media-canned-response";
    public static String GetTicketDetail= BaseURL+"/api/version3/ticket/get-ticket-detail";

    public static String ticketDispose= BaseURL+"/api/version3/ticket/dispose-task";
}
