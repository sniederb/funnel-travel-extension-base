package ch.want.funnel.extension.tripdata;

public class DiscardPayloadException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DiscardPayloadException() {
    }

    public DiscardPayloadException(final String message) {
        super(message);
    }

    public DiscardPayloadException(final Throwable cause) {
        super(cause);
    }

    public DiscardPayloadException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
