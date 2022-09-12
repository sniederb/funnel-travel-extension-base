package ch.want.funnel.extension.util.fileaccess;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;

public class FtpsDownloader extends FtpDownloader {

    FtpsDownloader(final URI resourceIdentifier, final String username, final String passwd) {
        super(resourceIdentifier, username, passwd);
    }

    @Override
    protected FTPClient createFtpClient() {
        final FTPClient client = new FTPSClient();
        try {
            client.addProtocolCommandListener(
                new PrintCommandListener(
                    new PrintWriter(new OutputStreamWriter(System.out, "UTF-8")), true));
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    protected void onClientLoggedIn(final FTPClient client) {
        // run "explicit SSL/TLS".
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
