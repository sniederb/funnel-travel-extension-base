package ch.want.funnel.extension.util.upload;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

class FtpUploader implements FileUploader {

    private static final int TIMEOUT_IN_MILLIS = 5 * 60 * 1000;
    private final URI resourceIdentifier;
    private final String username;
    private final String passwd;

    FtpUploader(final URI resourceIdentifier, final String username, final String passwd) {
        this.resourceIdentifier = resourceIdentifier;
        this.username = username;
        this.passwd = passwd;
    }

    @Override
    public void upload(final String targetFilename, final String tripData) throws IOException {
        final FTPClient client = createFtpClient();
        client.setConnectTimeout(TIMEOUT_IN_MILLIS);
        client.setDataTimeout(TIMEOUT_IN_MILLIS);
        try (InputStream is = new ByteArrayInputStream(tripData.getBytes(StandardCharsets.UTF_8))) {
            client.connect(resourceIdentifier.getHost());
            client.enterLocalPassiveMode();
            if (!client.login(username, passwd)) {
                throw new IOException("FTP Delivery: Failed to login: " + client.getReplyString());
            }
            if (resourceIdentifier.getPath() != null) {
                client.changeWorkingDirectory(resourceIdentifier.getPath());
            }
            client.setFileType(FTP.BINARY_FILE_TYPE);
            if (!client.storeFile(targetFilename, is)) {
                throw new IOException("FTP Delivery: Failed to store file on server: " + client.getReplyString());
            }
        } finally {
            closeAndDisconnectQuietly(client);
        }
    }

    protected FTPClient createFtpClient() {
        return new FTPClient();
    }

    private void closeAndDisconnectQuietly(final FTPClient client) {
        if (client != null) {
            try {
                client.logout();
                if (client.isConnected()) {
                    client.disconnect();
                }
            } catch (final Exception e) {
                // NOSONAR swallow exception
            }
        }
    }
}
