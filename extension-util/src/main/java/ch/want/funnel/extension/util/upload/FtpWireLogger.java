package ch.want.funnel.extension.util.upload;

import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpWireLogger implements ProtocolCommandListener {

    private static final Logger WIRE = LoggerFactory.getLogger("ch.want.funnel.extension.wire.apache-ftp");

    @Override
    public void protocolCommandSent(final ProtocolCommandEvent event) {
        if ("PASS".equalsIgnoreCase(event.getCommand())) {
            WIRE.debug("> PASS XXXXXXXXXXX");
        } else {
            WIRE.debug("> {}", event.getMessage() == null ? "" : event.getMessage().trim());
        }
    }

    @Override
    public void protocolReplyReceived(final ProtocolCommandEvent event) {
        WIRE.debug("< {}", event.getMessage() == null ? "" : event.getMessage().trim());
    }
}
