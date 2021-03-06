package ch.want.funnel.extension.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.want.funnel.extension.model.BookingStatus;

/**
 * HK = Holding Confirmed
 * HL = Holding Waitlist
 * BK = Passive Sold Segment, or Booked outside
 * BL = Booked outside and Waitlisted
 * BN = Booked outside and Requested
 * AK = Confirmed outside
 * AL = Waitlist outside
 * AN = Requested outside
 * GK = Passive Sold Segment
 * NO = Open Segment
 * PB = Holding Waitlist
 */
public class BookingStatusMapper {

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
     * {@code gdsStatus} can be 'HK' but also 'OK03'
     */
    public static BookingStatus getBookingStatus(final String gdsStatus) {
        if (gdsStatus != null) {
            final String twoLetterStatus = gdsStatus.length() > 2 ? gdsStatus.substring(0, 2) : gdsStatus;
            switch (twoLetterStatus) {
            case "OK":
            case "HK":
            case "AK":
                return BookingStatus.CONFIRMED;
            case "OP":
            case "HL":
            case "PB":
                return BookingStatus.OPTION;
            case "RQ":
            case "UC":
                return BookingStatus.PROCESSING;
            case "XX":
            case "UN":
                return BookingStatus.CANCELED;
            default:
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
