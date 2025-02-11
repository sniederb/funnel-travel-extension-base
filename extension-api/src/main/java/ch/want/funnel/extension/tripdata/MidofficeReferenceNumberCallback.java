package ch.want.funnel.extension.tripdata;

import java.util.Locale;
import java.util.Map;

import ch.want.funnel.extension.model.Booking;
import ch.want.funnel.extension.model.Trip;

/**
 * If an extension implements this interface, it will get called whenever the "Midoffice no." on the trip gets updated by a
 * {@link TripDataModifier} or {@link TripDataConsumer}.
 * This allows for extensions to write-back that reference into their respective systems.
 */
public interface MidofficeReferenceNumberCallback {

    /**
     * Use the {@link Booking#getMidofficeReferenceNumber()} to update the target system. Beware that {@code trip} can be null.
     */
    void midofficeReferenceNumberChanged(Booking booking, Trip trip, Map<String, Object> settings, Locale locale);
}
