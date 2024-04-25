package ch.want.funnel.extension.util.upload;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import ch.want.funnel.extension.model.Booking;

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

    /**
     * Build a filename assuming something like
     *
     * <pre>
     * trip-dump${yyyyMMdd}
     * </pre>
     *
     * Valid placeholder are:
     * <ul>
     * <li>${refNumber}</li>
     * <li>${uuid}</li>
     * <li>any other ${...} placeholder will be interpreted as date format</li>
     * </ul>
     *
     * @return
     */
    public static String buildTargetFilename(final Booking booking, final String targetFilePattern) {
        String result = targetFilePattern
            .replace("${refNumber}", booking.getReferenceNumber())
            .replace("${uuid}", booking.getUuid().toString());
        final int datePatternIndex = result.indexOf("${");
        if (datePatternIndex >= 0) {
            final String datePattern = result.substring(datePatternIndex + 2, result.indexOf('}', datePatternIndex));
            final DateFormat dateFormat = new SimpleDateFormat(datePattern);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            result = result.replace("${" + datePattern + "}", dateFormat.format(new Date()));
        }
        return result;
    }
}
