package ch.want.funnel.extension.model;

public enum BookingConsolidation {
    /**
     * This value indicates a stand-alone booking, representing the purchase as well as the retail side. This is the default value.
     */
    NONE,
    /**
     * This value indicates a purchase-only booking, consolidating the purchase side of (potentially) multiple retail bookings. This is
     * useful for group booking.
     */
    PURCHASE
}
