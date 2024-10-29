package ch.want.funnel.extension.tripdata;

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
