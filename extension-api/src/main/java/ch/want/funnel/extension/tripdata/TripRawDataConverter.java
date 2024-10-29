/*
 * Created on 08.03.2019
 */
package ch.want.funnel.extension.tripdata;

import java.util.Locale;
import java.util.Map;

import ch.want.funnel.extension.ExtensionResult;
import ch.want.funnel.extension.FunnelExtension;

/**
 * Implementations are capable of converting raw trip sources into a structure readable by funnel.travel.
 * Typical examples of implementations are webhook-based extensions which receive push notifications
 * from an external system.
 */
public interface TripRawDataConverter {

    /**
     * Throws a {@link InvalidPayloadException} if the payload is found to be invalid. Note that this method returning
     * without exception is no guarantee that {@link #convertRawSourceToTripData(byte[], Map, Locale)} will succeed.
     *
     * @param rawSource
     *            The source returned as {@link RawTripDataSource} by a {@link TripDataProducer}, or a webhook payload.
     * @param httpHeaders
     *            List of HTTP headers
     * @param settingValues
     *            Account-specific settings
     * @param locale
     */
    default void validatePayload(final byte[] rawSource, final Map<String, String> httpHeaders, final Map<String, Object> settingValues, final Locale locale) {
        // no-op. default is to assume valid
    }

    /**
     * For a given external source record, extract some key properties to be stored as metadata.
     *
     * @param rawSource
     *            The source returned as {@link RawTripDataSource} by a {@link TripDataProducer}, or the
     *            second-phase data for a {@link TripDataTwoPhasedProducer}
     * @param settings
     *            Account-specific settings
     * @param locale
     * @return
     */
    ContentMetadata extractMetadata(byte[] rawSource, Map<String, Object> settings, Locale locale);

    /**
     * <p>
     * Convert data from an external source, as part of a two phased production. This call is the
     * second step, after {@link TripDataProducer#getRawSources(Map, Locale)} has fetched raw sources.
     * </p>
     *
     * <p>
     * This method is NOT called if {@link #extractMetadata(byte[], Map, Locale)} has returned a {@link ContentMetadata}
     * with {@link ContentMetadata#isDiscardAsIntermediate()} of {@code true}
     * </p>
     *
     * <p>
     * The returned {@link ExtensionResult} can contain a message, in which case that is used as server response. Messages
     * starting with '&lt;' will be sent as {@code application/xml}, Messages starting with '{' or '[' will be sent as
     * {@code application/json}, all other messages will be sent as {@code text/plain}. If no message
     * is provided, a successful webhook call will reply with
     * </p>
     *
     * <pre>
     * Content type = application/json
     *
     * {"messageCode":"OK"}
     * </pre>
     *
     * @param rawSource
     *            The original trip data source such an email MIME message
     * @param settings
     *            A Map holding the keys defined in {@link FunnelExtension#getSettings()},
     *            and associated values with possible inheritance applied
     * @param locale
     *            Locale for error messages
     * @return A {@link ExtensionResult} with the {@link ExtensionResult#message} holding the webhook response
     */
    ExtensionResult convertRawSourceToTripData(byte[] rawSource, Map<String, Object> settings, Locale locale);
}
