package ch.want.funnel.extension.model;

public enum BookingStatus {
    /**
     * The booking source did not provide a status
     */
    UNDEFINED,
    /**
     * Tentative booking, to be confirmed later (OP, OX)
     */
    OPTION,
    /**
     * Booking is in the process of being confirmed (eg. waiting for hotel to respond, or a waitlisted segment). (RQ)
     */
    PROCESSING,
    /**
     * The booking is confirmed and active. (OK)
     */
    CONFIRMED,
    /**
     * The booking was canceled (XX)
     */
    CANCELED
}
