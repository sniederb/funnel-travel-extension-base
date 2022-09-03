package ch.want.funnel.extension.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public enum AgeGroup {

    INFANT("INF"), CHILD("CHD"), ADULT("ADT");

    private static final long INFANT_LIMIT_MONTHS = 2 * 12;
    private static final long CHILD_LIMIT_MONTHS = 12 * 12;
    private final String gdsCode;

    private AgeGroup(final String gdsCode) {
        this.gdsCode = gdsCode;
    }

    public static AgeGroup fromGdsCode(final String gdsCode) {
        return Arrays.stream(values())
            .filter(e -> e.gdsCode.equalsIgnoreCase(gdsCode))
            .findFirst()
            .orElse(AgeGroup.ADULT);
    }

    public static AgeGroup fromBirthdate(final LocalDate paxBirthDate, final LocalDate travelDate) {
        if ((paxBirthDate == null) || (travelDate == null)) {
            return AgeGroup.ADULT;
        }
        final long months = paxBirthDate.until(travelDate, ChronoUnit.MONTHS);
        if (months <= INFANT_LIMIT_MONTHS) {
            return AgeGroup.INFANT;
        }
        if (months <= CHILD_LIMIT_MONTHS) {
            return AgeGroup.CHILD;
        }
        return AgeGroup.ADULT;
    }
}
