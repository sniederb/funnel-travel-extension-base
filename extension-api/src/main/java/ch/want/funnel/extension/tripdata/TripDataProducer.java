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
public interface TripDataProducer extends TripDataWebhook {

    /**
     * @param settings
     *            A Map holding the keys defined in {@link FunnelExtension#getSettings()},
     *            and associated values with possible inheritance applied
     * @param locale
     *            Locale for error messages
     * @return A list of booking data structures in the format defined by {@link FunnelExtension#getDataFormat()}.
     *         BEWARE: for TripDataTwoPhasedProducer implementation, this list is expected to contain UTF-8 Strings
     *         containing an external ID which will later be referenced in the second phase.
     */
    List<byte[]> getRawSources(Map<String, Object> settings, Locale locale);
}
