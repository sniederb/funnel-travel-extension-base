/*
 * Created on 08.03.2019
 */
package ch.want.funnel.extension.tripdata;

import java.util.Locale;
import java.util.Map;

import ch.want.funnel.extension.FunnelExtension;

/**
 * Implementations are capable of converting raw trip sources into a structure readable by funnel.travel.
 * Typical examples of implementations are webhook-based extensions which receive push notifications
 * from an external system.
 */
public interface TripRawDataConverter {

    /**
     * For a given external source record, extract some key properties to be stored as metadata.
     *
     * @param rawSource
     *            The original trip data source such an email MIME message
     * @param settings
     *            Account-specific settings
     * @param locale
     * @return
     */
    ContentMetadata extractMetadata(byte[] rawSource, Map<String, Object> settings, Locale locale);

    /**
     * Convert data from an external source, as part of a two phased production. This call is the
     * second step, after {@link TripDataProducer#getRawSources(Map, Locale)} has fetched raw sources.
     *
     * @param rawSource
     *            The original trip data source such an email MIME message
     * @param settings
     *            A Map holding the keys defined in {@link FunnelExtension#getSettings()},
     *            and associated values with possible inheritance applied
     * @param locale
     *            Locale for error messages
     * @return A booking data structures in the format defined by {@link FunnelExtension#getDataFormat()}.
     */
    String convertRawSourceToTripData(byte[] rawSource, Map<String, Object> settings, Locale locale);
}
