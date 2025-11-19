package ch.want.funnel.extension.util.upload;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.util.TrustManagerUtils;

/**
 * FTPS implementation with encrypted data channel. Note that TLS/SSL session reuse for the data connection is <strong>not
 * supported</strong>.
 */
class FtpsUploader extends FtpUploader {

    FtpsUploader(final URI resourceIdentifier, final String username, final String passwd) {
        super(resourceIdentifier, username, passwd);
    }

    @Override
    protected FTPClient createFtpClient() {
        // The FTPSClient class defaults to an explicit TLS/SSL (recommended).
        // In a rare case you need an implicit TLS/SSL, use new FTPSClient(true).
        final FTPSClient ftpClient = new FTPSClient();
        ftpClient.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());
        return ftpClient;
    }

    @Override
    protected void afterConnect(final FTPClient client) throws IOException {
        // see https://issues.apache.org/jira/browse/NET-235
        ((FTPSClient) client).execPROT("P");
        super.afterConnect(client);
    }
}
