package ch.want.funnel.extension.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.want.funnel.extension.model.BookingStatus;

/**
 * Map standard booking status codes to funnel.travel {@link BookingStatus}.
 */
public final class BookingStatusMapper {

    private static final Logger LOG = LoggerFactory.getLogger(BookingStatusMapper.class);
    /**
     * You use the GK status code to enter a confirmed ghost segment or GL for a waitlisted ghost segment.
     */
    public static final Set<String> GHOST_STATUS_CODES;
    static {
        final Set<String> codes = new HashSet<>();
        codes.add("GL");
        codes.add("GK");
        GHOST_STATUS_CODES = Collections.unmodifiableSet(codes);
    }

    private BookingStatusMapper() {
    }

    /**
     * {@code gdsStatus} can be 'HK' but also 'OK03'.
     * See e.g. <a href='https://support.travelport.com/webhelp/uapi/Content/Air/Shared_Air_Topics/PNR_Status_Codes.htm'>Galileo PNR status
     * codes</a>,
     * of which only the commonly used codes are mapped here.
     */
    public static BookingStatus getBookingStatus(final String gdsStatus) {
        if (gdsStatus != null) {
            final String twoLetterStatus = gdsStatus.length() > 2 ? gdsStatus.substring(0, 2) : gdsStatus;
            switch (twoLetterStatus) {
            case "OK": // ticketed and confirmed
            case "HK": // Holds confirmed, seat confirmed on a flight
            case "AK": // passive confirmed
            case "TK": // Schedule Change
            case "GK": // confirmed ghost segment
            case "YK": // Hold confirmed Airline space
                return BookingStatus.CONFIRMED;
            case "OP":
            case "HL": // Holds waitlist
            case "GL": // waitlisted ghost segmentl
            case "TL": // Schedule change on waitlist
            case "PA": // Priority waitlist
            case "PB": // Priority waitlist
            case "PC": // Priority waitlist
            case "PD": // Priority waitlist
            case "UU": // Unable to confirm. Waitlist requested
                return BookingStatus.OPTION;
            case "RQ": // On request
            case "HQ": // Space prev. request
            case "UC": // Unable to confirm or waitlist
            case "PN": // Pending need
                return BookingStatus.PROCESSING;
            case "XX":
            case "UN": // Flight cancelled by airline
                return BookingStatus.CANCELED;
            default:
                LOG.error("Got unknown code {}, mapping to UNDEFINED", twoLetterStatus);
                return BookingStatus.UNDEFINED;
            }
        }
        return BookingStatus.CONFIRMED;
    }

    /**
     * This method returns a <strong>possible</strong> value for a GDS status. Refering to
     * {@link #getBookingStatus(String)} it is clear that multiple status are mapped to the
     * same value, so this method simply returns on of those.
     *
     * @param funnelStatus
     * @return
     */
    public static String getBookingStatus(final BookingStatus funnelStatus) {
        if (funnelStatus != null) {
            switch (funnelStatus) {
            case CONFIRMED:
                return "OK";
            case OPTION:
                return "OP";
            case CANCELED:
                return "XX";
            case PROCESSING:
                return "RQ";
            case UNDEFINED:
            default:
                return "";
            }
        }
        return "";
    }
}
