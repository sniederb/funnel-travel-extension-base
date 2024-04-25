package ch.want.funnel.extension.util.upload;

import java.net.URI;

public class FileUploaderFactory {

    private FileUploaderFactory() {
        // util class
    }

    public static FileUploader getUploader(final URI resourceIdentifier, final String username, final String passwd) {
        if ("sftp".equalsIgnoreCase(resourceIdentifier.getScheme())) {
            return new SftpUploader(resourceIdentifier, username, passwd);
        }
        if ("ftp".equalsIgnoreCase(resourceIdentifier.getScheme())) {
            return new FtpUploader(resourceIdentifier, username, passwd);
        }
        if ("ftps".equalsIgnoreCase(resourceIdentifier.getScheme())) {
            return new FtpsUploader(resourceIdentifier, username, passwd);
        }
        if ("https".equalsIgnoreCase(resourceIdentifier.getScheme())) {
            return new HttpsUploader(resourceIdentifier, username, passwd);
        }
        throw new IllegalArgumentException("Don't know how to send/upload to " + resourceIdentifier);
    }
}
