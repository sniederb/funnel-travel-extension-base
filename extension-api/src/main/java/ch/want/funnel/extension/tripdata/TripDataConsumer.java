/*
 * (c) Copyright 2019 WaNT GmbH
 */
package ch.want.funnel.extension.tripdata;

import java.util.Locale;
import java.util.Map;

import ch.want.funnel.extension.ExtensionResult;
import ch.want.funnel.extension.FunnelExtension;
import ch.want.funnel.extension.model.Booking;
import ch.want.funnel.extension.model.TravelerProfileAffinity;
import ch.want.funnel.extension.model.Trip;

public interface TripDataConsumer {

    /**
     * Consume a single booking. Note that this method might be called multiple times on the same instance, with
     * different bookings.
     *
     * @param booking
     *            Booking data structure including raw sources
     * @param trip
     *            The trip associated with the {@code booking}
     * @param settings
     *            A Map holding the keys defined in {@link FunnelExtension#getSettings()},
     *            and associated values with possible inheritance applied
     * @param locale
     *            Locale for error messages, based on funnel.travel setup. Note that the trip has a {@link Trip#getLocale()} which
     *            is primarily derived from traveler profile preferences, but otherwise defaults to this value.
     * @return The processing result
     */
    ExtensionResult consume(Booking booking, Trip trip, Map<String, Object> settings, Locale locale);

    /**
     * Indicate how badly this extension needs extended traveler profile data. Use
     * {@link TravelerProfileAffinity#NONE} if you don't know.
     */
    default TravelerProfileAffinity getTravelerProfileAffinity() {
        return TravelerProfileAffinity.NONE;
    }

    /**
     * funnel.travel will store a booking state when queuing extension execution. By default, extensions
     * will receive that snapshot. Override this method and return false if the current booking data
     * should be read from the database.
     */
    default boolean isPayloadFromDatabase() {
        return false;
    }

    /**
     * Called by funnel.travel before destroying the instance
     */
    default void processingComplete() {
    }
}
