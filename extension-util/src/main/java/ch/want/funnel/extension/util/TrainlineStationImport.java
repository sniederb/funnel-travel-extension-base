package ch.want.funnel.extension.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class TrainlineStationImport {

    private static final int CSV_INDEX_NAME = 1;
    private static final int CSV_INDEX_SNCF_ID = 16;
    private static final int CSV_INDEX_BENERAIL_ID = 49;
    private static final String GITHUB_SOURCE = "https://github.com/trainline-eu/stations/raw/master/stations.csv";
    private static final String RESOURCE_FILE = "/ch/want/funnel/extension/railstation-names.properties";
    private URLConnection githubCsvUrlConnection;

    public static void main(final String[] args) {
        try {
            new TrainlineStationImport().readStationsToCsv();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    TrainlineStationImport() {
    }

    private void readStationsToCsv() throws IOException {
        String line;
        boolean headerline = true;
        try (BufferedReader reader = createBufferedReader();
            BufferedWriter bufferWriter = createBufferedWriterToResource()) {
            while ((line = reader.readLine()) != null) {
                if (headerline) {
                    bufferWriter.write("# This data is based derived from the Trainline stations.csv on GitHub");
                    bufferWriter.newLine();
                    headerline = false;
                } else {
                    writeCsvLine(line, bufferWriter);
                }
            }
        } finally {
            IOUtils.close(githubCsvUrlConnection);
        }
    }

    protected void writeCsvLine(final String line, final BufferedWriter bufferWriter) throws IOException {
        final String[] csvValues = line.split(";");
        if (StringUtils.isNotBlank(csvValues[CSV_INDEX_SNCF_ID])) {
            bufferWriter.write("sncf." + csvValues[CSV_INDEX_SNCF_ID] + "=" + replaceNonLatin1Chars(csvValues[CSV_INDEX_NAME]));
            bufferWriter.newLine();
        }
        if (StringUtils.isNotBlank(csvValues[CSV_INDEX_BENERAIL_ID])) {
            bufferWriter.write("benerail." + csvValues[CSV_INDEX_BENERAIL_ID] + "=" + replaceNonLatin1Chars(csvValues[CSV_INDEX_NAME]));
            bufferWriter.newLine();
        }
    }

    private BufferedReader createBufferedReader() throws IOException {
        final URL githubCsvUrl = new URL(GITHUB_SOURCE);
        githubCsvUrlConnection = githubCsvUrl.openConnection();
        return new BufferedReader(new InputStreamReader(githubCsvUrlConnection.getInputStream(), StandardCharsets.UTF_8));
    }

    /**
     * note that Files.newBufferedWriter() creates a write which throws UnmappableCharacterException, but we
     * want something more robust.
     */
    private BufferedWriter createBufferedWriterToResource() throws IOException {
        final URL targetUrl = TrainlineStationImport.class.getResource(RESOURCE_FILE);
        final URI targetUrlAtSource = URI.create(targetUrl.toString().replaceFirst("target/classes", "src/main/resources"));
        final Path filePath = Paths.get(targetUrlAtSource);
        return new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(filePath, StandardOpenOption.TRUNCATE_EXISTING), StandardCharsets.ISO_8859_1));
    }

    private String replaceNonLatin1Chars(final String s) {
        return s.replace('’', '\'').replace('—', '-').replace('œ', 'o');
    }
}
