package ch.want.funnel.extension.util.fileaccess;

import java.net.URI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FileDownloaderFactoryTest {

    @ParameterizedTest
    @CsvSource({ "ftp", "ftps", "sftp" })
    void getDownloader(final String prototol) {
        final FileDownloader result = FileDownloaderFactory.getDownloader(URI.create(prototol + "://localhost/myfolder"), "foobar", "secret");
        Assertions.assertNotNull(result);
    }
}
