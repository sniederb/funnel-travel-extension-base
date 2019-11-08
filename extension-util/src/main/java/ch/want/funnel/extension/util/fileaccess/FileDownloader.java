package ch.want.funnel.extension.util.fileaccess;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface FileDownloader {

    Collection<File> download(String localFolder) throws IOException;
}
