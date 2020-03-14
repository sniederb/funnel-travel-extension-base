package ch.want.funnel.extension;

import java.util.Optional;

import ch.want.funnel.extension.model.Booking;
import ch.want.funnel.extension.tripdata.TripDataProducer;

/**
 * Result class for extensions to indicate processing state.
 * <ul>
 * <li>If processing failed exceptionally, and re-processing is desirable, throw an exception</li>
 * <li>If processing basically succeeded, this class allows for returning a processing message
 * and/or a return code.</li>
 * <li>For {@link TripDataProducer} extensions being called by a webhook, the {@link #message} will be returned as a HTTP response.</li>
 * </ul>
 */
public class ExtensionResult {

    public static final ExtensionResult OK = new ExtensionResult("");
    private final String message;
    private final int returnCode;
    private final Booking booking;

    public ExtensionResult(final String message) {
        this(null, 0, message);
    }

    public ExtensionResult(final int returnCode) {
        this(null, returnCode, "");
    }

    public ExtensionResult(final int returnCode, final String message) {
        this(null, returnCode, message);
    }

    public ExtensionResult(final Booking booking) {
        this(booking, 0, "");
    }

    public ExtensionResult(final Booking booking, final String message) {
        this(booking, 0, message);
    }

    public ExtensionResult(final Booking booking, final int returnCode) {
        this(booking, returnCode, "");
    }

    public ExtensionResult(final Booking booking, final int returnCode, final String message) {
        this.booking = booking;
        this.returnCode = returnCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public Optional<Booking> getBooking() {
        return Optional.ofNullable(booking);
    }
}
