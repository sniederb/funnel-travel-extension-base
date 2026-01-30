package ch.want.funnel.extension.tripdata;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import ch.want.funnel.extension.PaymentProducerResult;
import ch.want.funnel.extension.SettingItemValueType;

/**
 * {@link TripDataConsumer} may choose to implement this interface if the consuming system (usually a midoffice) handles payments, and
 * offers a mechanism to feed payment information back into funnel.travel.
 */
public interface PaymentProducer {

    /**
     * Fetch a list of payments to process. Implementations will typically hold an {@link SettingItemValueType#INTERNAL} setting to restrict
     * the fetch to only new payments.
     */
    List<PaymentProducerResult> getPayments(Map<String, Object> settings, Locale locale);
}
