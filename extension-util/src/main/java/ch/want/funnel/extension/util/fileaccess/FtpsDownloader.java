package ch.want.funnel.extension.util.fileaccess;

import java.net.URI;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;

public class FtpsDownloader extends FtpDownloader {

    FtpsDownloader(final URI resourceIdentifier, final String username, final String passwd) {
        super(resourceIdentifier, username, passwd);
    }

    @Override
    protected FTPClient createFtpClient() {
        return new FTPSClient();
    }
}
