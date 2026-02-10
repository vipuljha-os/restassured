package CruiseBooking.Model;

public class CabinConfig {

    private final int adults;
    private final int children;
    private final int infants;

    public CabinConfig(int adults, int children, int infants) {
        if (adults < 1) {
            throw new IllegalArgumentException("At least 1 adult is required per cabin");
        }
        int total = adults + children + infants;
        if (total > 4) {
            throw new IllegalArgumentException("Total guests per cabin cannot exceed 4, got " + total);
        }
        this.adults = adults;
        this.children = children;
        this.infants = infants;
    }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public int getInfants() {
        return infants;
    }

    public int getTotalGuests() {
        return adults + children + infants;
    }

    @Override
    public String toString() {
        return "A=" + adults + "_C=" + children + "_I=" + infants;
    }
}
