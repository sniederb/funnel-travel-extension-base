package ch.want.funnel.extension.tripdata;

/**
 * A {@link TripDataProducer} where the account identification is embedded in the trip payload. These producers
 * typically require a single, global endpoint. Consequently, a {@link TripDataGlobalProducer} provides means
 * to identify the account from a payload.
 */
public interface TripDataGlobalProducer extends TripDataProducer {

    /**
     * From the data of an external source, extract the setting key/value pair which will identify the account against
     * which the payload should be processed
     *
     * @param payload
     *            Source which is typically provided by a webhook.
     * @return
     */
    AccountMetadata getAccountMetadata(byte[] payload);
}
