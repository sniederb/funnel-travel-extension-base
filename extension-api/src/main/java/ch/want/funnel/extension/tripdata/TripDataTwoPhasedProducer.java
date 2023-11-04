package ch.want.funnel.extension.tripdata;

import java.util.Locale;
import java.util.Map;

/**
 * <p>
 * A two-phased producer is used for asynchronous processing on the producer system. Typically, the first call
 * is a timed call to retrieve partial data (e.g. an email, or a booking ID), which is then sent to a third-party
 * system for processing. That third-party system will later push a result back to this producer.
 * </p>
 *
 * <p>
 * In order to match the two phases, the call to {@link #getRawSources(Map, Locale)} returns a collection of IDs,
 * the later call to {@link #convertRawSourceToTripData(byte[], Map, Locale)} should then match up with those
 * IDs.
 * </p>
 */
public interface TripDataTwoPhasedProducer extends TripDataProducer {

    /**
     * Implementations can override this method to trigger two-phased processing on specific
     * data sources only (e.g. when the extension configuration allows for a selection using two-phased
     * or not two-phased)
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
