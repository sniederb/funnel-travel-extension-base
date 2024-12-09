package ch.want.funnel.extension.util.upload;

import java.io.IOException;

public interface FileUploader {

    /**
     * Test connection, will throw an exception if connection fails.
     */
    void ping() throws IOException;

    void upload(final String targetFilename, final String tripData) throws IOException;
}
