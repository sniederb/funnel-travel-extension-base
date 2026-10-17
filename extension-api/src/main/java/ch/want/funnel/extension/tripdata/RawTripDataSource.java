package ch.want.funnel.extension.tripdata;

import java.io.Serializable;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * This class holds any sort of source for trip data (eg. a MIME email message, or an XML structure)
 */
public class RawTripDataSource implements Serializable {

    private static final long serialVersionUID = 1L;
    private final byte[] source;
    private final String uniqueSourceId;
    private final Map<String, String> httpHeaders;
    private final URL requestUrl;

    /**
     * <p>
     * Create a non-HTTP source object.
     * </p>
     * The {@code source} will be persisted in funnel.travel and presented to the user in the booking section.
     * (must match {@link ContentMetadata#getMimeType()}
     */
    public RawTripDataSource(final byte[] source, final String uniqueSourceId) {
        this(source, uniqueSourceId, Collections.emptyMap(), null);
    }

    /**
     * <p>
     * Create a source object originating from an HTTP request.
     * </p>
     * The {@code source} will be persisted in funnel.travel and presented to the user in the booking section.
     * (must match {@link ContentMetadata#getMimeType()}
     *
     * @param source
     * @param uniqueSourceId
     * @param httpHeaders
     * @param requestUrl
     */
    public RawTripDataSource(final byte[] source, final String uniqueSourceId, final Map<String, String> httpHeaders, final URL requestUrl) {
        this.source = source;
        this.uniqueSourceId = uniqueSourceId;
        this.httpHeaders = httpHeaders;
        this.requestUrl = requestUrl;
    }

    /**
     * Create a source object based on another {@link RawTripDataSource}, while providing an updated {@code uniqueSourceId}
     *
     * @param source
     * @param uniqueSourceId
     */
    public RawTripDataSource(final RawTripDataSource source, final String uniqueSourceId) {
        this(source.source, uniqueSourceId, source.httpHeaders, source.requestUrl);
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

    /**
     * Returns the HTTP headers if this source originates from an HTTP call. Otherwise, and empty map is returned.
     *
     * @return Never null
     */
    public Map<String, String> getHttpHeaders() {
        return httpHeaders == null ? Collections.emptyMap() : httpHeaders;
    }

    public Optional<URL> getRequestUrl() {
        return Optional.ofNullable(requestUrl);
    }
}
