package ch.want.funnel.extension.model;

public enum BookingStatus {
    /**
     * The booking source did not provide a status, or the status is something like "Open", "Edit" or "Quote". Also, and "fail" or "error"
     * state is mapped to this status.
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
     * The booking was canceled. (XX)
     */
    CANCELED
}
