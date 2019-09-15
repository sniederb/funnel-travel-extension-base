/*
 * (c) Copyright 2019 WaNT GmbH
 */
package ch.want.funnel.extension.tripdata;

import java.util.Locale;
import java.util.Map;

import ch.want.funnel.extension.FunnelExtension;
import ch.want.funnel.extension.model.Booking;

public interface TripDataConsumer {

    /**
     *
     * @param booking
     *            Booking data structure
     * @param settings
     *            A Map holding the keys defined in {@link FunnelExtension#getSettings()},
     *            and associated values with possible inheritance applied
     * @param locale
     *            Locale for error messages
     * @return
     */
    void consume(Booking booking, Map<String, Object> settings, Locale locale);
}
