package ch.want.funnel.extension.tripdata;

import java.util.Locale;
import java.util.Map;

/**
 * If an extension implements this interface, funnel.travel will display a "test connection" button on the extension settings card.
 */
public interface TestableConnection {

    /**
     * Establish a connection using the provided {@code settings}. Implementation should
     * throw a {@link RuntimeException} subclass if the connection fails.
     */
    void testConnection(Map<String, Object> settings, Locale locale);
}
