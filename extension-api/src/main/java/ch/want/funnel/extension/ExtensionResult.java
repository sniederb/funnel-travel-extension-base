package ch.want.funnel.extension;

import java.util.Optional;

import ch.want.funnel.extension.model.Booking;
import ch.want.funnel.extension.tripdata.TripDataConsumer;
import ch.want.funnel.extension.tripdata.TripDataModifier;

/**
 * Result class for {@link TripDataModifier} and {@link TripDataConsumer} extensions
 * to indicate processing state.
 * <ul>
 * <li>If processing failed exceptionally, and re-processing is desirable, throw an exception</li>
 * <li>If processing basically succeeded, this class allows for returning a processing message
 * and/or a return code.</li>
 * </ul>
 */
public class ExtensionResult {

    private final String message;
    private final int returnCode;
    private final Booking modifiedBooking;
    public static ExtensionResult OK = new ExtensionResult("");

    public ExtensionResult(final String message) {
        this(null, 0, message);
    }

    public ExtensionResult(final int returnCode) {
        this(null, returnCode, "");
    }

    public ExtensionResult(final int returnCode, final String message) {
        this(null, returnCode, message);
    }

    public ExtensionResult(final Booking modifiedBooking, final String message) {
        this(modifiedBooking, 0, message);
    }

    public ExtensionResult(final Booking modifiedBooking, final int returnCode) {
        this(modifiedBooking, returnCode, "");
    }

    public ExtensionResult(final Booking modifiedBooking, final int returnCode, final String message) {
        this.modifiedBooking = modifiedBooking;
        this.returnCode = returnCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public Optional<Booking> getModifiedBooking() {
        return Optional.ofNullable(modifiedBooking);
    }
}
