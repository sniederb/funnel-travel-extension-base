package ch.want.funnel.extension.tripdata;

import ch.want.funnel.extension.ExtensionResult;

/**
 * Exception thrown on a PUSH to funnel.travel where the payload is found to be not wanted. Note that typically it is funnel.travel core
 * which throws this exception. Extension implementations are recommended to return an {@link ExtensionResult} without a booking.
 */
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
