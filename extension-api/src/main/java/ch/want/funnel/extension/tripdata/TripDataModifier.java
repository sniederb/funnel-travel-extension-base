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

/**
 * Implementations of this interface take an existing trip structure, and return the structure after some form
 * of modification.
 */
public interface TripDataModifier {

    /**
     *
     * @param booking
     *            Booking data structure
     * @param settings
     *            A Map holding the keys defined in {@link FunnelExtension#getSettings()},
     *            and associated values with possible inheritance applied
     * @param locale
     *            Locale for error messages
     * @return The processing result. If the extension recognizes that no modification is needed, the {@code modifiedBooking} should
     *         be set to null in the {@link ExtensionResult}.
     *
     */
    ExtensionResult modify(Booking booking, Map<String, Object> settings, Locale locale);

    /**
     * Indicate how badly this extension needs extended traveler profile data. Use
     * {@link TravelerProfileAffinity#NONE} if you don't know.
     *
     * @return
     */
    TravelerProfileAffinity getTravelerProfileAffinity();
}
