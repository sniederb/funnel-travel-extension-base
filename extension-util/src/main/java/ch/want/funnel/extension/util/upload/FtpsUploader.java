package ch.want.funnel.extension.util.upload;

import java.net.URI;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.util.TrustManagerUtils;

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
}
