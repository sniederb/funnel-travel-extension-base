package ch.want.funnel.extension.util.fileaccess;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;

class FtpDownloader implements FileDownloader {

    private static final int TIMEOUT_IN_MILLIS = 5 * 60 * 1000;
    private final URI resourceIdentifier;
    private final String username;
    private final String passwd;

    FtpDownloader(final URI resourceIdentifier, final String username, final String passwd) {
        this.resourceIdentifier = resourceIdentifier;
        this.username = username;
        this.passwd = passwd;
    }

    @Override
    public Collection<File> download(final String localFolder) throws IOException {
        final File localDir = new File(localFolder);
        if (!localDir.exists()) {
            localDir.mkdirs();
        }
        final List<File> downloadedFiles = new ArrayList<>();
        final FTPClient client = createFtpClient();
        client.setConnectTimeout(TIMEOUT_IN_MILLIS);
        client.setDataTimeout(TIMEOUT_IN_MILLIS);
        try {
            client.connect(resourceIdentifier.getHost());
            client.enterLocalPassiveMode();
            if (!client.login(username, passwd)) {
                throw new IOException("FTP: Failed to login: " + client.getReplyString());
            }
            onClientLoggedIn(client);
            if ((resourceIdentifier.getPath() != null) && !client.changeWorkingDirectory(resourceIdentifier.getPath())) {
                throw new FileAccessWarnException("Failed to change to directory " + resourceIdentifier.getPath());
            }
            client.setFileType(FTP.BINARY_FILE_TYPE);
            final FTPFile[] remoteFiles = client.listFiles(".");
            for (final FTPFile remoteFile : remoteFiles) {
                if (remoteFile.getType() == FTPFile.FILE_TYPE) {
                    final File targetFile = new File(localDir, remoteFile.getName());
                    try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(targetFile))) {
                        if (!client.retrieveFile(remoteFile.getName(), outputStream)) {
                            throw new IOException("FTP Delivery: Failed to download file: " + client.getReplyString());
                        }
                    }
                    downloadedFiles.add(targetFile);
                    client.deleteFile(remoteFile.getName());
                }
            }
        } catch (final FTPConnectionClosedException ex) {
            throw new FileAccessWarnException("Connection closed", ex);
        } finally {
            closeAndDisconnectQuietly(client);
        }
        return downloadedFiles;
    }

    protected FTPClient createFtpClient() {
        return new FTPClient();
    }

    @SuppressWarnings("unused")
    protected void onClientLoggedIn(final FTPClient client) {
        // no-op
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
