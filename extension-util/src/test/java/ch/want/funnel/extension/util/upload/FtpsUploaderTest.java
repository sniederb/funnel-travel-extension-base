package ch.want.funnel.extension.util.upload;

import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("live test")
class FtpsUploaderTest {

    @Test
    void liveTest() throws IOException {
        final URI resourceIdentifier = URI.create("ftps://storage16.hostfactory.ch/funnel-dump");
        final FtpsUploader testee = new FtpsUploader(resourceIdentifier, "vpsbkp2105", "YjyseJABUVu4");
        testee.upload("funnel-test.txt", "Did this arrive");
    }
}
