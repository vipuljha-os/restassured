package Generic;

public class BigbasketRoutes {
    public static String BaseURL = "https://bigbasket.kapdesk.com";

    //user module
    public static String BigBasketLogin = BaseURL + "/ms/auth/mobile-authenticate-user/";
    public static String addTicket = BaseURL + "/api/version3/ticket/add-ticket";
    public static String BigBasketGetTicketList = BaseURL + "/api/version3/ticket/get-ticket-list";
    public static String cannedResponse = BaseURL + "/api/version3/ticket/get-social-media-canned-response";
    public static String GetTicketDetail = BaseURL + "/api/version3/ticket/get-ticket-detail";
    public static String JunkTicket = BaseURL + "/api/version3/ticket/junk-task";
    public static String ticketDispose = BaseURL + "/api/version3/ticket/dispose-task";
    public static String GetOrderDetail = BaseURL + "/ms/ticketcustomer/order/detail";
    public static String GetOrderList = BaseURL + "/ms/ticketcustomer/order/list";
    public static String BigBasketReopen = BaseURL + "/api/version3/ticket/reopen-task";

    public static String GetOtherOrderDetail = BaseURL + "/ms/ticketcustomer/order/other-detail";

}
