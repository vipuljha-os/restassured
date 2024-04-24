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
    public static String orderDetail= BaseURL+"/ms/ticketcustomer/order/detail";

    public static String ticketTemplate=BaseURL+"/ms/ticket-configuration/ticket-configuration/search-ticket-template";

    public static String reopenTicket= BaseURL+"/api/version3/ticket/reopen-task";

    public static String advertisement= BaseURL+"/ms/ticketcustomer/order/other-detail";
    public static String emailTemplates= BaseURL+"/api/version3/ticket/email-templates";
    public static String addNote= BaseURL+"/api/version3/ticket/add-note";
    public static String getWatcherList= BaseURL+"/ms/ticket-action/ticket-action/get-ticket-watcher";
    public static String performanceDashBoard= BaseURL+"/ms/dashboard/performance-dashboard";
}
