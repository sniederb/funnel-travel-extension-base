package ch.want.funnel.extension.util.fileaccess;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;

/**
 * Download over FTPS explicit
 */
public class FtpsDownloader extends FtpDownloader {

    FtpsDownloader(final URI resourceIdentifier, final String username, final String passwd) {
        super(resourceIdentifier, username, passwd);
    }

    @Override
    protected FTPClient createFtpClient() {
        // defaults to "explicit SSL/TLS".
        final FTPClient client = new FTPSClient();
        if (Boolean.parseBoolean(System.getProperty("funnel-verbose", "false"))) {
            client.addProtocolCommandListener(
                new PrintCommandListener(
                    new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8)), true));
        }
        return client;
    }

    @Override
    protected void onClientLoggedIn(final FTPClient client) {
        final FTPSClient ftpsClient = (FTPSClient) client;
        try {
            // Set protection buffer size
            ftpsClient.execPBSZ(0);
            // Set data channel protection to private
            ftpsClient.execPROT("P");
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to enable SSL/TLS on data channel");
        }
    }
}
