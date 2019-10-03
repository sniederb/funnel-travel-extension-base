/*
 * Created on 03.10.2019
 */
package ch.want.funnel.extension.util;

public final class DataUtils {

    private DataUtils() {
        // c'tor
    }

    public static String getCountry(final String funnelDestinationCode) {
        if (funnelDestinationCode == null) {
            return null;
        }
        if (funnelDestinationCode.length() != 6) {
            throw new IllegalArgumentException("Expected a destination code in the format DDD/CC, but got " + funnelDestinationCode);
        }
        return funnelDestinationCode.substring(4);
    }

    public static String getDestination(final String funnelDestinationCode) {
        if (funnelDestinationCode == null) {
            return null;
        }
        if (funnelDestinationCode.length() != 6) {
            throw new IllegalArgumentException("Expected a destination code in the format DDD/CC, but got " + funnelDestinationCode);
        }
        return funnelDestinationCode.substring(0, 3);
    }
}
