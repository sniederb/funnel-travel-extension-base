/*
 * Created on 17 Jan 2019
 */
package ch.want.funnel.extension.tripdata;

import java.util.Locale;
import java.util.Map;

public interface TripDataTwoPhasedProducer extends TripDataProducer {

    /**
     * From the data of an external source, extract the identifying ID which matches those produced by
     * {@link TripDataProducer#createTripData(Map, Locale)}.
     *
     * @param dataFromExternalSource
     * @return
     */
    String getExternalId(byte[] rawsource);
}
