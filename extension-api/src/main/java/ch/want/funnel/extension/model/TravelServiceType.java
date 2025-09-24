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
     * Segmented type. This service type typically has only one (1) segment, but is in nature still a "from A to B" service. A cruise will
     * often have information more akin to a {@link #HOTEL}, use custom fields to map such data.
     * Ordinal: 5
     */
    SHIP,
    /**
     * Unsegmented type. Use this type with care. Often, instead of the "package", individual services are processed. Using this type can
     * make sense if e.g. the individual services are not priced, and an additional entry of this type is used to hold the price.
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