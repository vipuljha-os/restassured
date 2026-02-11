package CruiseBooking.Model;

public class BookingResult {

    private final BookingScenario scenario;
    private String step5Status;
    private String step6Status;
    private String step7Status;
    private String bookingId;
    private String paymentLink;
    private String errorMessage;

    public BookingResult(BookingScenario scenario) {
        this.scenario = scenario;
        this.step5Status = "SKIPPED";
        this.step6Status = "SKIPPED";
        this.step7Status = "SKIPPED";
    }

    public BookingScenario getScenario() {
        return scenario;
    }

    public String getStep5Status() {
        return step5Status;
    }

    public void setStep5Status(String step5Status) {
        this.step5Status = step5Status;
    }

    public String getStep6Status() {
        return step6Status;
    }

    public void setStep6Status(String step6Status) {
        this.step6Status = step6Status;
    }

    public String getStep7Status() {
        return step7Status;
    }

    public void setStep7Status(String step7Status) {
        this.step7Status = step7Status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String toCsvRow() {
        return String.join(",",
                String.valueOf(scenario.getCruiseId()),
                quote(scenario.getItineraryTitle()),
                quote(scenario.getSailingDate()),
                scenario.getSailingType(),
                scenario.getCategoryId(),
                String.valueOf(scenario.getCabinConfig().getAdults()),
                String.valueOf(scenario.getCabinConfig().getChildren()),
                String.valueOf(scenario.getCabinConfig().getInfants()),
                step5Status,
                step6Status,
                step7Status,
                quote(bookingId != null ? bookingId : ""),
                quote(paymentLink != null ? paymentLink : ""),
                quote(errorMessage != null ? errorMessage : "")
        );
    }

    public static String csvHeader() {
        return "CruiseId,Itinerary,SailingDate,SailingType,CategoryId,"
                + "Adults,Children,Infants,Step5,Step6,Step7,BookingId,PaymentLink,Error";
    }

    private static String quote(String value) {
        if (value == null) return "\"\"";
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
