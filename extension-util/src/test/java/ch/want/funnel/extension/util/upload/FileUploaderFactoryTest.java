package ch.want.funnel.extension.util.upload;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import ch.want.funnel.extension.model.Booking;

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

    @ParameterizedTest
    @CsvSource({ "trip-dump${yyyyMMdd}.json,trip-dump#yyyyMMdd#.json", //
        "${refNumber}.raw,PBEBCH7230944634.raw", //
        "${uuid}_${refNumber}_${yyyyMMdd-HHmmss}.xml,73bed396-4d35-4921-8276-075d65604ac8_PBEBCH7230944634_#yyyyMMdd-HHmmss#.xml" })
    void buildTargetFilename(final String pattern, final String expected) throws IOException {
        final Booking booking = new Booking();
        booking.setReferenceNumber("PBEBCH7230944634");
        booking.setUuid(UUID.fromString("73bed396-4d35-4921-8276-075d65604ac8"));
        // act
        final String result = FileUploaderFactory.buildTargetFilename(booking, pattern);
        // assert
        Assertions.assertEquals(replaceDatePattern(expected), result);
    }

    private static String replaceDatePattern(final String s) {
        final int firstHash = s.indexOf('#');
        final int lastHash = s.lastIndexOf('#');
        if ((firstHash >= 0) && (lastHash >= 0)) {
            final String pattern = s.substring(firstHash + 1, lastHash);
            final DateFormat dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            final String datePart = dateFormat.format(new Date());
            return s.substring(0, firstHash) + datePart + s.substring(lastHash + 1);
        }
        return s;
    }
}
