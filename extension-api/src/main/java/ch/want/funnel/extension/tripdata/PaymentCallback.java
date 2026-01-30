package ch.want.funnel.extension.tripdata;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import ch.want.funnel.extension.model.Booking;
import ch.want.funnel.extension.model.BookingPayment;
import ch.want.funnel.extension.model.Trip;

/**
 * {@link TripDataProducer} may choose to implement this interface if the delivering system offers a mechanism to feed payment information
 * back.
 */
public interface PaymentCallback {

    /**
     * Offer the implementation a chance to process newly imported {@code payments}. Note that these payment will also be part of the
     * provided {@link Booking#getPayments()}, but that collection might contain additional payments.
     *
     * @return True if the payment callback executed successfully, false otherwise.
     */
    boolean paymentImported(List<BookingPayment> payments, Booking booking, Trip trip, Map<String, Object> settingValues, Locale locale);
}
