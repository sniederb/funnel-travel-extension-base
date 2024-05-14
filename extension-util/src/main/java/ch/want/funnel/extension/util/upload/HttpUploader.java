package ch.want.funnel.extension.util.upload;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUploader implements FileUploader {

    private static final Logger LOG = LoggerFactory.getLogger(HttpUploader.class);
    private static final int TIMEOUT_IN_MILLIS = 5 * 60 * 1000;
    private final URI resourceIdentifier;
    private final CredentialsProvider credentialsProvider;
    private final RequestConfig requestConfig;

    HttpUploader(final URI resourceIdentifier, final String username, final String passwd) {
        this.resourceIdentifier = resourceIdentifier;
        if (StringUtils.isNoneBlank(username, passwd)) {
            credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, passwd));
        } else {
            credentialsProvider = null;
        }
        requestConfig = RequestConfig.custom()
            .setConnectTimeout(TIMEOUT_IN_MILLIS * 1000)
            .setConnectionRequestTimeout(TIMEOUT_IN_MILLIS * 1000)
            .setSocketTimeout(TIMEOUT_IN_MILLIS * 1000)
            .build();
    }

    @Override
    public void upload(final String targetFilename, final String tripData) throws IOException {
        final CloseableHttpClient client = createClient();
        final HttpPost httpPost = createHttpPost(targetFilename, tripData);
        try (CloseableHttpResponse response = client.execute(httpPost)) {
            if (response.getStatusLine().getStatusCode() > 299) {
                LOG.warn("Call failed with {}, entity {}", response.getStatusLine(), response.getEntity());
                throw new IllegalStateException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }
        }
    }

    protected HttpPost createHttpPost(final String targetFilename, final String tripData) {
        final byte[] bytes = tripData.getBytes(StandardCharsets.ISO_8859_1);
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("file", bytes, ContentType.DEFAULT_BINARY, targetFilename);
        final HttpPost httpPost = new HttpPost(resourceIdentifier);
        httpPost.setEntity(builder.build());
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.MULTIPART_FORM_DATA.getMimeType());
        return httpPost;
    }

    protected CloseableHttpClient createClient() {
        return HttpClientBuilder.create()
            .setDefaultCredentialsProvider(credentialsProvider)
            .setDefaultRequestConfig(requestConfig)
            .build();
    }
}
