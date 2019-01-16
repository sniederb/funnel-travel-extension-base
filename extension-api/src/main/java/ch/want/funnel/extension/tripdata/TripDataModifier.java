/*
 * (c) Copyright 2019 WaNT GmbH
 */
package ch.want.funnel.extension.tripdata;

import java.util.Locale;
import java.util.Map;

import ch.want.funnel.extension.FunnelExtension;

/**
 * Implementations of this interface take an existing trip structure, and return the structure after some form
 * of modification.
 */
public interface TripDataModifier {

    /**
     *
     * @param tripData
     *            Booking data structure in the format defined by {@link FunnelExtension#getDataFormat()}
     * @param settings
     *            A Map holding the keys defined in {@link FunnelExtension#getSettings()},
     *            and associated values with possible inheritance applied
     * @param locale
     *            Locale for error messages
     * @return
     */
    String modify(String tripData, Map<String, Object> settings, Locale locale);
}
