package ch.want.funnel.extension.util.upload;

import java.io.IOException;

public interface FileUploader {

    void upload(final String targetFilename, final String tripData) throws IOException;
}
