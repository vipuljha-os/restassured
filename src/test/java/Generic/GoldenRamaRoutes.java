package Generic;

public class GoldenRamaRoutes {

    public static String BaseURL= "https://goldenrama.kapturecrm.com";

    //user module
    public static String GoldenRamalogin = BaseURL+"/ms/auth/mobile-authenticate-user/";
    public static String addTicket= BaseURL+"/api/version3/ticket/add-ticket";
    public static String addAttachment = BaseURL+"/api/version3/ticket/add-task-attachment";
    public static String addNote = BaseURL+"/api/version3/ticket/add-note";
    public static String GetTicketList = BaseURL+"/api/version3/ticket/get-ticket-list";
    public static String getAttachment = BaseURL+"/api/version3/ticket/get-ticket-attachments";
    public static String cannedResponse = BaseURL+"/api/version3/ticket/get-social-media-canned-response";
    public static String GetTicketDetail= BaseURL+"/api/version3/ticket/get-ticket-detail";
    public static String JunkTicket = BaseURL+"/api/version3/ticket/junk-task";
    public static String ticketDispose = BaseURL+"/api/version3/ticket/dispose-task";
    public static String mergeTicket = BaseURL+"/api/version3/ticket/merge-task";
    public static String unjunkTicket = BaseURL+"/api/version3/ticket/unjunk-task";
    public static String assignToTicket = BaseURL+"/api/version3/ticket/task-assignment";
    public static String reopenTicket = BaseURL+"/api/version3/ticket/reopen-task";
    public static String GetOrderDetail = BaseURL+"/ms/ticketcustomer/order/detail";
    public static String GetOrderList = BaseURL+"/ms/ticketcustomer/order/list";
    public static String GetOtherOrderDetail = BaseURL+"/api/version3/ticket/dispose-task";
}
