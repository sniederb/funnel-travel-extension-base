/*
 * Created on 24 Nov 2017
 */
package ch.want.funnel.extension.model;

public enum TravelServiceType {
    /**
     * Segmented type
     * Ordinal: 0
     */
    FLIGHT,
    /**
     * Unsegmented type
     * Ordinal: 1
     */
    HOTEL,
    /**
     * Unsegmented type
     * Ordinal: 2
     */
    CARRENTAL,
    /**
     * Segmented type
     * Ordinal: 3
     */
    TRAIN,
    /**
     * Segmented type
     * Ordinal: 4
     */
    BUS,
    /**
     * Segmented type
     * Ordinal: 5
     */
    SHIP,
    /**
     * Ordinal: 6
     */
    PACKAGE,
    /**
     * Unsegmented type. For special cases of a misc service such as refund or transfer, see constants for
     * {@link SingleSegment#getServiceTypeCode()} in {@link SingleSegment}
     * Ordinal: 7
     */
    MISC;

    public static boolean hasSegments(final TravelServiceType type) {
        return type == FLIGHT || type == TRAIN || type == BUS || type == SHIP;
    }
}