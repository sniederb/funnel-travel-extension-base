package ch.want.funnel.extension.util.upload;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class for simple {@link CloseableHttpClient} with a timeout {@link RequestConfig}. Only the main web application should call
 * {@link #shutdown()}.
 */
public class HttpClientFactory {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientFactory.class);
    private static final ConcurrentMap<Integer, CloseableHttpClient> CLIENTS = new ConcurrentHashMap<>();

    private HttpClientFactory() {
    }

    /**
     * Get a {@link CloseableHttpClient} without any {@link RequestConfig} apart from timeouts. Clients requiring e.g. a
     * {@link CredentialsProvider} need to create and close the {@link CloseableHttpClient} themselves.
     *
     * @param timeoutMillis
     * @return
     */
    public static CloseableHttpClient getHttpClient(final int timeoutMillis) {
        return CLIENTS.computeIfAbsent(timeoutMillis, i -> {
            final RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(timeoutMillis)
                .setConnectionRequestTimeout(timeoutMillis)
                .setSocketTimeout(timeoutMillis)
                .build();
            return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .build();
        });
    }

    public static synchronized void shutdown() {
        CLIENTS.entrySet().forEach(entry -> {
            try {
                LOG.debug("Closing HTTP client with {}ms timeout", entry.getKey());
                entry.getValue().close();
            } catch (final IOException e) {
                LOG.warn("Failed to properly close HTTP client", e);
            }
        });
        CLIENTS.clear();
    }
}
