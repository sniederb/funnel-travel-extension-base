/*
 * (c) Copyright 2019 WaNT GmbH
 */
package ch.want.funnel.extension.tripdata;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import ch.want.funnel.extension.FunnelExtension;

/**
 * Implementations of this interface retrieve trip data from other sources such as e-mail servers,
 * booking websites or a GDS.
 */
public interface TripDataProducer {

    /**
     * @param settings
     *            A Map holding the keys defined in {@link FunnelExtension#getSettings()},
     *            and associated values with possible inheritance applied
     * @param locale
     *            Locale for error messages
     * @return A list of booking data structures in the format defined by {@link FunnelExtension#getDataFormat()}
     */
    List<String> createTripData(Map<String, Object> settings, Locale locale);
}
