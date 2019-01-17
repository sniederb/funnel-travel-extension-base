/*
 * Created on 17 Jan 2019
 */
package ch.want.funnel.extension.tripdata;

import java.util.Locale;
import java.util.Map;

import ch.want.funnel.extension.FunnelExtension;

public interface TripDataTwoPhasedProducer extends TripDataProducer {

    /**
     * From the data of an external source, extract the identifying ID which matches those produced by
     * {@link TripDataProducer#createTripData(Map, Locale)}.
     * 
     * @param dataFromExternalSource
     * @return
     */
    String getExternalId(String dataFromExternalSource);

    /**
     * Convert data from an external source, as part of a two phased production. This call is the
     * second step, after {@link TripDataProducer#createTripData(Map, Locale)} has triggered the
     * first step.
     *
     * @param dataFromExternalSource
     * @param settings
     *            A Map holding the keys defined in {@link FunnelExtension#getSettings()},
     *            and associated values with possible inheritance applied
     * @param locale
     *            Locale for error messages
     * @return A booking data structures in the format defined by {@link FunnelExtension#getDataFormat()}.
     */
    String convert(String dataFromExternalSource, Map<String, Object> settings, Locale locale);
}
