package CruiseBooking.Model;

public class BookingScenario {

    private final long cruiseId;
    private final String itineraryTitle;
    private final String sessionId;
    private final String sailingDate;
    private final String sailingType;
    private final String categoryId;
    private final CabinConfig cabinConfig;

    public BookingScenario(long cruiseId, String itineraryTitle, String sessionId,
                           String sailingDate, String sailingType,
                           String categoryId, CabinConfig cabinConfig) {
        this.cruiseId = cruiseId;
        this.itineraryTitle = itineraryTitle;
        this.sessionId = sessionId;
        this.sailingDate = sailingDate;
        this.sailingType = sailingType;
        this.categoryId = categoryId;
        this.cabinConfig = cabinConfig;
    }

    public long getCruiseId() {
        return cruiseId;
    }

    public String getItineraryTitle() {
        return itineraryTitle;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getSailingDate() {
        return sailingDate;
    }

    public String getSailingType() {
        return sailingType;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public CabinConfig getCabinConfig() {
        return cabinConfig;
    }

    public String getLabel() {
        return "Cruise" + cruiseId + "_" + itineraryTitle + "_" + sailingDate
                + "_Cat" + categoryId + "_" + cabinConfig;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
