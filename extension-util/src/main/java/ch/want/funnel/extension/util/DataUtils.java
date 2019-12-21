package ch.want.funnel.extension.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.IntStream;

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

    /**
     * Parse an {@code association} string to a collection of integers. Valid input values:
     * <ul>
     * <li>1
     * <li>1,2
     * <li>1,2-4
     * <li>1,2-4,7,9-11
     * </ul>
     *
     */
    public static Collection<Integer> mapAssociationToNumbers(final String association) {
        final Collection<Integer> result = new HashSet<>();
        if (association == null) {
            return result;
        }
        for (final String s : association.split("[,;]")) {
            final int dashPosition = s.indexOf('-');
            if (dashPosition >= 0) {
                final String from = s.substring(0, dashPosition);
                final String to = s.substring(dashPosition + 1);
                IntStream.rangeClosed(Integer.parseInt(from), Integer.parseInt(to)).boxed().forEach(result::add);
            } else {
                result.add(Integer.parseInt(s));
            }
        }
        return result;
    }
}
