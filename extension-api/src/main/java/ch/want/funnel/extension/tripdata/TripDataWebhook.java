/*
 * Created on 15 Jan 2019
 */
package ch.want.funnel.extension.tripdata;

import java.util.Locale;
import java.util.Map;

/**
 * Implementations of this interface take a payload from a webhook, and convert it to a funnel.travel payload.
 */
public interface TripDataWebhook {

    String convert(String webhookPayload, Map<String, Object> settings, Locale locale);
}
