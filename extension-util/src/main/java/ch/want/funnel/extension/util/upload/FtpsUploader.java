package ch.want.funnel.extension.util.upload;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.util.TrustManagerUtils;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FTPS implementation with encrypted data channel. Note that TLS/SSL session reuse for the data connection is <strong>not
 * supported</strong>.
 */
class FtpsUploader extends FtpUploader {

    private static final Logger LOG = LoggerFactory.getLogger(FtpsUploader.class);

    FtpsUploader(final URI resourceIdentifier, final String username, final String passwd) {
        super(resourceIdentifier, username, passwd);
    }

    @Override
    protected FTPClient createFtpClient() {
        FTPSClient ftpClient;
        try {
            final TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
            // see https://issues.apache.org/jira/browse/NET-739 why downgrading to TLS 1.2
            final SSLContext tls12Ctx = SSLContexts.custom()
                .setProtocol("TLSv1.2")
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();
            ftpClient = new FTPSClient(tls12Ctx);
        } catch (final KeyManagementException | KeyStoreException | NoSuchAlgorithmException ex) {
            LOG.warn("Failed to get TLS 1.2 SSL context", ex);
            // The FTPSClient class defaults to an explicit TLS/SSL (recommended).
            // In a rare case you need an implicit TLS/SSL, use new FTPSClient(true).
            ftpClient = new FTPSClient();
            ftpClient.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());
        }
        return ftpClient;
    }

    @Override
    protected void afterConnect(final FTPClient client) throws IOException {
        // see https://issues.apache.org/jira/browse/NET-235
        ((FTPSClient) client).execPROT("P");
        super.afterConnect(client);
    }
}
