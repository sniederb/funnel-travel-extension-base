package ch.want.funnel.extension.model;

/**
 * This enum describes the very basic lifecycle states of an entire booking (with respect to the customer).
 * Note that there <strong>is no CANCELLED state</strong>, as this can be derived from travel service states.
 */
public enum BookingLifecycleState {
    /**
     * The customer is exploring options, without any commitment. Travel services are typically
     * OP or RQ, but could also be OK/CF on a refundable service.
     */
    EXPLORING,
    /**
     * The customer has committed, possibly also paid. Travel services are often OK/CF, but some
     * might still be RQ (and some cases even OP).
     */
    BOOKED
}
