package ch.want.funnel.extension.util.upload;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;

class FileUploaderFactoryTest {

    @Test
    void getUploader_sftp() throws Exception {
        final URI resourceIdentifier = new URI("sftp://filestore.funnel.travel/export");
        final FileUploader fileUploader = FileUploaderFactory.getUploader(resourceIdentifier, "foo", "bar");
        assertEquals(SftpUploader.class, fileUploader.getClass());
    }

    @Test
    void getUploader_ftp() throws Exception {
        final URI resourceIdentifier = new URI("ftp://filestore.funnel.travel/export");
        final FileUploader fileUploader = FileUploaderFactory.getUploader(resourceIdentifier, "foo", "bar");
        assertEquals(FtpUploader.class, fileUploader.getClass());
    }

    @Test
    void getUploader_ftps() throws Exception {
        final URI resourceIdentifier = new URI("ftps://filestore.funnel.travel/export");
        final FileUploader fileUploader = FileUploaderFactory.getUploader(resourceIdentifier, "foo", "bar");
        assertEquals(FtpsUploader.class, fileUploader.getClass());
    }

    @Test
    void getUploader_https() throws Exception {
        final URI resourceIdentifier = new URI("https://filestore.funnel.travel/export");
        final FileUploader fileUploader = FileUploaderFactory.getUploader(resourceIdentifier, "foo", "bar");
        assertEquals(HttpsUploader.class, fileUploader.getClass());
    }

    @Test
    void getUploader_unknown() throws Exception {
        final URI resourceIdentifier = new URI("http://filestore.funnel.travel/export");
        assertThrows(IllegalArgumentException.class, () -> FileUploaderFactory.getUploader(resourceIdentifier, "foo", "bar"));
    }
}
