package ch.want.funnel.extension;

import java.util.Optional;

import ch.want.funnel.extension.model.Booking;
import ch.want.funnel.extension.tripdata.RawTripDataSource;
import ch.want.funnel.extension.tripdata.TripDataConsumer;
import ch.want.funnel.extension.tripdata.TripDataModifier;
import ch.want.funnel.extension.tripdata.TripDataProducer;
import ch.want.funnel.extension.tripdata.TripRawDataConverter;

/**
 * Result class for extensions to indicate processing state.
 * <ul>
 * <li>If processing failed exceptionally, and re-processing is desirable, throw an exception</li>
 * <li>If processing basically succeeded, this class allows for returning a processing message and/or a return code.</li>
 * <li>If processing worked, but the execution should still show up in the list of failed extension calls, return an error code
 * {@link #EXECUTION_ERROR_RETURN_CODE}</li>
 * <li>For {@link TripDataProducer} extensions being called by a webhook, the {@link #message} will be returned as a HTTP response.</li>
 * </ul>
 */
public class ExtensionResult {

    public static final int EXECUTION_ERROR_RETURN_CODE = 999;
    public static final ExtensionResult OK = new ExtensionResult("");
    private final String message;
    private final int returnCode;
    private final Booking booking;
    private RawTripDataSource updatedRawSource;
    private String midofficeReferenceNumber;

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

    /**
     * For a webhook {@link TripRawDataConverter}, this message is returned as HTTP response.
     */
    public String getMessage() {
        return message;
    }

    public int getReturnCode() {
        return returnCode;
    }

    /**
     * Only relevant for {@link TripDataProducer} and {@link TripDataModifier}. For {@link TripDataConsumer}, this method is not used.
     */
    public Optional<Booking> getBooking() {
        return Optional.ofNullable(booking);
    }

    /**
     * Set this if funnel.travel should not persist the value passed into {@link TripRawDataConverter#convertRawSourceToTripData(byte[],
     * Map<String, Object>, Locale)} as raw source, but rather the one set here.
     */
    public ExtensionResult setUpdatedRawSource(final RawTripDataSource updatedRawSource) {
        this.updatedRawSource = updatedRawSource;
        return this;
    }

    public Optional<RawTripDataSource> getUpdatedRawSource() {
        return Optional.ofNullable(updatedRawSource);
    }

    /**
     * If set on a {@link TripDataConsumer}, funnel.travel will update the internal {@link Booking#getMidofficeReferenceNumber()}.
     */
    public String getMidofficeReferenceNumber() {
        return midofficeReferenceNumber;
    }

    public void setMidofficeReferenceNumber(final String midofficeReferenceNumber) {
        this.midofficeReferenceNumber = midofficeReferenceNumber;
    }
}
