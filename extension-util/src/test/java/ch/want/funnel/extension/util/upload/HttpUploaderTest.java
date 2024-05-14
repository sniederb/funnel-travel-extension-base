package ch.want.funnel.extension.util.upload;

import java.io.IOException;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicStatusLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class HttpUploaderTest {

    @Test
    void testUpload() throws IOException {
        final CloseableHttpClient httpClient = httpClient();
        final HttpUploader testee = new MockHttpsUploader(httpClient);
        // act
        testee.upload("DFGHMS-20240105.txt", "Some data here");
        // assert
        final ArgumentCaptor<HttpUriRequest> requestCaptor = ArgumentCaptor.forClass(HttpUriRequest.class);
        Mockito.verify(httpClient).execute(requestCaptor.capture());
        final HttpUriRequest request = requestCaptor.getValue();
        Assertions.assertNotNull(request);
        final Header contentTypeHeader = request.getFirstHeader(HttpHeaders.CONTENT_TYPE);
        Assertions.assertNotNull(contentTypeHeader);
        Assertions.assertEquals("multipart/form-data", contentTypeHeader.getValue());
    }

    private CloseableHttpClient httpClient() throws ClientProtocolException, IOException {
        final CloseableHttpResponse httpResponse = Mockito.mock(CloseableHttpResponse.class);
        final StatusLine statusLine = new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "");
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        final CloseableHttpClient client = Mockito.mock(CloseableHttpClient.class);
        Mockito.when(client.execute(Mockito.any(HttpUriRequest.class))).thenReturn(httpResponse);
        return client;
    }

    private static class MockHttpsUploader extends HttpUploader {

        private final CloseableHttpClient httpClient;

        MockHttpsUploader(final CloseableHttpClient httpClient) {
            super(URI.create("https://localhost/httpsuploader"), "junit", "mysecret");
            this.httpClient = httpClient;
        }

        @Override
        protected CloseableHttpClient createClient() {
            return httpClient;
        }
    }
}
