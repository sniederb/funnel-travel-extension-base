/*
 * Created on 08.03.2019
 */
package ch.want.funnel.extension.tripdata;

import java.io.Serializable;

/**
 * This class holds any sort of source for trip data (eg. a MIME email message, or an XML structure)
 */
public class RawTripDataSource implements Serializable {

    private static final long serialVersionUID = 1L;
    private final byte[] source;
    private final String uniqueSourceId;

    public RawTripDataSource(final byte[] source, final String uniqueSourceId) {
        this.source = source;
        this.uniqueSourceId = uniqueSourceId;
    }

    /**
     * @return The source ID which - for a {@link TripDataTwoPhasedProducer} implementation - must match what that
     *         {@link TripDataTwoPhasedProducer#getExternalId(byte[])} returns.
     */
    public String getUniqueSourceId() {
        return uniqueSourceId;
    }

    public byte[] getSource() {
        return source;
    }
}
