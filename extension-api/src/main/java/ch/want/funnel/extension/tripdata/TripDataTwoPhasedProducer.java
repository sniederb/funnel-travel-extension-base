package ch.want.funnel.extension.tripdata;

import java.util.Locale;
import java.util.Map;

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
