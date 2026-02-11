package CruiseBooking.Util;

import CruiseBooking.Model.CabinConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuestComboGenerator {

    private static final int MIN_ADULTS = 1;
    private static final int MAX_GUESTS = 4;

    private static final List<CabinConfig> ALL_COMBOS;

    static {
        List<CabinConfig> combos = new ArrayList<>();
        for (int adults = MIN_ADULTS; adults <= MAX_GUESTS; adults++) {
            for (int children = 0; children <= MAX_GUESTS - adults; children++) {
                for (int infants = 0; infants <= MAX_GUESTS - adults - children; infants++) {
                    combos.add(new CabinConfig(adults, children, infants));
                }
            }
        }
        ALL_COMBOS = Collections.unmodifiableList(combos);
    }

    public static List<CabinConfig> getAllCombos() {
        return ALL_COMBOS;
    }

    public static int getComboCount() {
        return ALL_COMBOS.size();
    }
}
