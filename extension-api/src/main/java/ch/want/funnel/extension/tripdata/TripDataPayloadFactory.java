package ch.want.funnel.extension.tripdata;

import java.util.Locale;
import java.util.Map;

import ch.want.funnel.extension.model.Booking;
import ch.want.funnel.extension.model.Trip;

/**
 * If an extension implements this interface, funnel.travel will display a "download payload" link on the
 * trip details so an agent can see what the payload would be given the current trip data.
 */
public interface TripDataPayloadFactory {

    /**
     * Pure function generating a payload. Calling this method must have <strong>no side-effects</strong>.
     */
    PayloadResponse getPayload(Booking booking, Trip trip, Map<String, Object> settings, Locale locale);
}
