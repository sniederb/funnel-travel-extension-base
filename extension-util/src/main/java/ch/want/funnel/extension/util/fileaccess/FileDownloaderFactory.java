package ch.want.funnel.extension.util.fileaccess;

import java.net.URI;

/**
 * Factory-class to create a {@link FileDownloader} instance. Currently supported
 * schemes are:
 * <ul>
 * <li>sftp</li>
 * <li>ftp</li>
 * <li>ftps</li>
 * </ul>
 */
public class FileDownloaderFactory {

    private FileDownloaderFactory() {
        // util class
    }

    public static FileDownloader getDownloader(final URI resourceIdentifier, final String username, final String passwd) {
        if ("sftp".equalsIgnoreCase(resourceIdentifier.getScheme())) {
            return new SftpDownloader(resourceIdentifier, username, passwd);
        }
        if ("ftp".equalsIgnoreCase(resourceIdentifier.getScheme())) {
            return new FtpDownloader(resourceIdentifier, username, passwd);
        }
        if ("ftps".equalsIgnoreCase(resourceIdentifier.getScheme())) {
            return new FtpsDownloader(resourceIdentifier, username, passwd);
        }
        throw new IllegalArgumentException("Don't know how to download from " + resourceIdentifier);
    }
}
