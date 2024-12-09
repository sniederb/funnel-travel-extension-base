package ch.want.funnel.extension.util.upload;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

class SftpUploader implements FileUploader {

    private static final Logger LOG = LoggerFactory.getLogger(SftpUploader.class);
    private static final int TIMEOUT_IN_MILLIS = 5 * 60 * 1000;
    private final URI resourceIdentifier;
    private final String username;
    private final String passwd;
    private Session session = null;
    private ChannelSftp sftpChannel = null;

    SftpUploader(final URI resourceIdentifier, final String username, final String passwd) {
        this.resourceIdentifier = resourceIdentifier;
        this.username = username;
        this.passwd = passwd;
    }

    @Override
    public void ping() throws IOException {
        try {
            initSession();
            connectToSubfolder();
            listRemoteDirectory();
            close();
        } catch (final JSchException | SftpException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    @Override
    public void upload(final String targetFilename, final String tripData) throws IOException {
        try {
            initSession();
            connectToSubfolder();
            put(tripData.getBytes(StandardCharsets.UTF_8), targetFilename);
            close();
        } catch (final JSchException | SftpException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    private void initSession() throws JSchException {
        final int port = resourceIdentifier.getPort() > 0 ? resourceIdentifier.getPort() : 22;
        session = new JSch().getSession(username, resourceIdentifier.getHost(), port);
        session.setPassword(passwd);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setTimeout(TIMEOUT_IN_MILLIS);
    }

    private void connectToSubfolder() throws JSchException, SftpException {
        session.connect();
        sftpChannel = (ChannelSftp) session.openChannel("sftp");
        sftpChannel.connect();
        String path = resourceIdentifier.getPath();
        if (path != null && path.length() > 0) {
            // path could be relative or absolute
            final String ftpHome = sftpChannel.pwd();
            if (!path.startsWith(ftpHome) && path.startsWith("/")) {
                // path is relative, strip leading slash
                path = path.substring(1);
            }
            sftpChannel.cd(path);
        }
    }

    private void put(final byte[] data, final String remoteFilename) throws IOException {
        try {
            sftpChannel.put(new ByteArrayInputStream(data), remoteFilename, ChannelSftp.OVERWRITE);
        } catch (final SftpException ex) {
            // see com.jcraft.jsch.ChannelSftp for values
            throw new IOException("Failed to upload to " + remoteFilename + ", error code " + ex.id, ex);
        }
    }

    private void listRemoteDirectory() throws SftpException {
        final Vector<?> ls = sftpChannel.ls("*"); // NOSONAR
        for (final Object entry : ls) {
            LOG.info("{}", entry);
        }
    }

    private void close() {
        sftpChannel.exit();
        sftpChannel.disconnect();
        session.disconnect();
    }
}
