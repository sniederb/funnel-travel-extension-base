package ch.want.funnel.extension.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public enum TravelerAgeGroup {

    INF("INF"), CHD("CHD"), ADT("ADT");

    private static final long INFANT_LIMIT_MONTHS = 2 * 12;
    private static final long CHILD_LIMIT_MONTHS = 12 * 12;
    private final String gdsCode;

    private TravelerAgeGroup(final String gdsCode) {
        this.gdsCode = gdsCode;
    }

    public static TravelerAgeGroup fromGdsCode(final String gdsCode) {
        return Arrays.stream(values())
            .filter(e -> e.gdsCode.equalsIgnoreCase(gdsCode))
            .findFirst()
            .orElse(TravelerAgeGroup.ADT);
    }

    public static TravelerAgeGroup fromBirthdate(final LocalDate paxBirthDate, final LocalDate travelDate) {
        if ((paxBirthDate == null) || (travelDate == null)) {
            return TravelerAgeGroup.ADT;
        }
        final long months = paxBirthDate.until(travelDate, ChronoUnit.MONTHS);
        if (months <= INFANT_LIMIT_MONTHS) {
            return TravelerAgeGroup.INF;
        }
        if (months <= CHILD_LIMIT_MONTHS) {
            return TravelerAgeGroup.CHD;
        }
        return TravelerAgeGroup.ADT;
    }
}
