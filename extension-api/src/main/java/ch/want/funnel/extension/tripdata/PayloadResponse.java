package ch.want.funnel.extension.tripdata;

/**
 * Compound class for a payload response, similar to Spring's ResponseEntity.
 * Note that the {@code mediaType} may contain a charset.
 */
public class PayloadResponse {

    private final byte[] payload;
    private final String mediaType;

    public PayloadResponse(final byte[] payload, final String mediaType) {
        this.payload = payload;
        this.mediaType = mediaType;
    }

    public byte[] getPayload() {
        return payload;
    }

    public String getMediaType() {
        return mediaType;
    }
}
