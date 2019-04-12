/*
 * (c) Copyright 2019 WaNT GmbH
 */
package ch.want.funnel.extension.tripdata;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import ch.want.funnel.extension.FunnelExtension;

/**
 * Implementations of this interface take an existing trip structure, and return the structure after some form
 * of modification.
 */
public interface TripDataModifier {

    /**
     *
     * @param tripData
     *            Booking data structure in the format defined by {@link FunnelExtension#getDataFormat(Map)}
     * @param settings
     *            A Map holding the keys defined in {@link FunnelExtension#getSettings()},
     *            and associated values with possible inheritance applied
     * @param locale
     *            Locale for error messages
     * @return The modified trip content. If the extension recognizes that no modification is needed, an {@link Optional#empty()} is
     *         returned.
     *
     */
    Optional<String> modify(String tripData, Map<String, Object> settings, Locale locale);
}
