package ch.want.funnel.extension.util.fileaccess;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * SFTP download using JSch. Note that default is {@code StrictHostKeyChecking = no}.
 */
class SftpDownloader implements FileDownloader {

    private static final int TIMEOUT_IN_MILLIS = 5 * 60 * 1000;
    private final URI resourceIdentifier;
    private final String username;
    private final String passwd;
    private Session session = null;
    private ChannelSftp sftpChannel = null;
    private boolean strictHostKeyChecking = false;

    SftpDownloader(final URI resourceIdentifier, final String username, final String passwd) {
        this.resourceIdentifier = resourceIdentifier;
        this.username = username;
        this.passwd = passwd;
    }

    public SftpDownloader enableStrictHostKeyChecking() {
        this.strictHostKeyChecking = true;
        return this;
    }

    @Override
    public Collection<File> download(final String localFolder) throws IOException {
        final List<File> downloadedFiles = new ArrayList<>();
        try {
            final File localDir = new File(localFolder);
            if (!localDir.exists()) {
                localDir.mkdirs();
            }
            initSession();
            connectToSubfolder();
            @SuppressWarnings("unchecked")
            final Vector<ChannelSftp.LsEntry> filelist = sftpChannel.ls("."); // NOSONAR
            for (final ChannelSftp.LsEntry file : filelist) {
                final String filename = file.getFilename();
                final File targetFile = new File(localDir, filename);
                sftpChannel.get(file.getFilename(), targetFile.getAbsolutePath());
                downloadedFiles.add(targetFile);
                sftpChannel.rm(filename);
            }
            close();
        } catch (final JSchException | SftpException e) {
            throw new IOException(e.getMessage(), e);
        }
        return downloadedFiles;
    }

    private void initSession() throws JSchException {
        final int port = resourceIdentifier.getPort() > 0 ? resourceIdentifier.getPort() : 22;
        session = new JSch().getSession(username, resourceIdentifier.getHost(), port);
        session.setPassword(passwd);
        if (!strictHostKeyChecking) {
            session.setConfig("StrictHostKeyChecking", "no");
        }
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

    private void close() {
        sftpChannel.exit();
        sftpChannel.disconnect();
        session.disconnect();
    }
}
