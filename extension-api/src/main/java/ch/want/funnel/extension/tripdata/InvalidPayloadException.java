package ch.want.funnel.extension.tripdata;

/**
 * {@link RuntimeException} indicating that a provided payload to a {@link TripDataProducer}
 * was found to be invalid by that producer (e.g. sending an AIR file to a Nezasa producer extensionn)
 */
public class InvalidPayloadException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidPayloadException() {
    }

    public InvalidPayloadException(final String message) {
        super(message);
    }

    public InvalidPayloadException(final Throwable cause) {
        super(cause);
    }

    public InvalidPayloadException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
