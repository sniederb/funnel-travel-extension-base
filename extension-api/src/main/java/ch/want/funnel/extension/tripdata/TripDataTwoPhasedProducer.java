package ch.want.funnel.extension.tripdata;

import java.util.Locale;
import java.util.Map;

/**
 * A two-phased producer is used for asynchronous processing on the producer system. Typically, in the first
 * phase, the call to {@link #getRawSources(Map, Locale)} only returns a collection of IDs, while delegating
 * work to an external system. The latter later calls a webhook with a payload, which is then passed on
 * to {@link #convertRawSourceToTripData(byte[], Map, Locale)}.
 */
public interface TripDataTwoPhasedProducer extends TripDataProducer {

    /**
     * Implementations can override this method to trigger two-phased processing on specific
     * data sources only.
     *
     * @param rawTripDataSource
     *            The {@link RawTripDataSource} as returned by {@link TripDataProducer#getRawSources(Map, Locale)}
     * @param settings
     * @return
     */
    default boolean isTwoPhased(final RawTripDataSource rawTripDataSource, final Map<String, Object> settings) {
        return true;
    }

    /**
     * From the data of an external source, extract the identifying ID which matches {@link RawTripDataSource#getUniqueSourceId()}
     * produced by {@link TripDataProducer#getRawSources(Map, Locale)}.
     *
     * @param twoPhaseSource
     *            Source which is typically provided by a webhook. This is NOT necessarily equal
     *            to the original trip data source, but possibly some intermediate data structure.
     * @return
     */
    String getExternalId(byte[] twoPhaseSource);
}
