package ch.want.funnel.extension.model;

public enum PriceItemType {
    DEFAULT, //
    /**
     * Flight or train fare. {@link PriceItem}s of this type should be attached to a {@link TransportDocument}, which itself has a
     * {@link TransportDocument#getTraveler()} set
     */
    FARE_PER_PAX, //
    /**
     * Flight tax. {@link PriceItem}s of this type should be attached to a {@link TransportDocument}, which itself has a
     * {@link TransportDocument#getTraveler()} set
     */
    TAX_PER_PAX, //
    /**
     * 'Off ticket' fees (OB/OC/OD fees). {@link PriceItem}s of this type should be attached to a {@link TransportDocument}, which itself
     * has a
     * {@link TransportDocument#getTraveler()} set
     */
    OFF_TICKET_PER_PAX, //
    /**
     * General-purpose pricing, but unlike {@link #DEFAULT} this is per pax. {@link PriceItem}s of this type should be attached to a
     * {@link TransportDocument}, which itself has a {@link TransportDocument#getTraveler()} set
     */
    AUX_PER_PAX, //
    /**
     * General-purpose pricing, behaves identical to {@link #DEFAULT}.
     */
    AUX_PER_BOOKING, //
    /**
     * Booking fee like a penalty fee or a ticketing fee. {@link PriceItem}s of this type attached to a {@link TransportDocument} are often
     * merged with {@link #FARE_PER_PAX} by the consumer.
     */
    FEE_PER_PAX,
    /**
     * Indicates that this {@link PriceItem} will need to be paid on-site, by the customer. Midoffices typically will not include these
     * prices in the revenue stream, but might want to show the information to the customer.
     */
    ONSITE,
    /**
     * Surcharge per pax. {@link PriceItem}s of this type must have a {@link PriceItem#getPurchasePrice()} of zero.
     */
    SURCHARGE_PER_PAX,
    /**
     * Surcharge per booking. {@link PriceItem}s of this type must have a {@link PriceItem#getPurchasePrice()} of zero.
     */
    SURCHARGE_PER_BOOKING,
    /**
     * Supply-side surcharge or commission. {@link PriceItem}s of this type must have a {@link PriceItem#getAmount()} of zero. Typically the
     * {@link PriceItem#getPurchasePrice()} is negative (for a commission), but this type can also be used for supplier-only surcharges
     * (which do not get passed on to retail).
     */
    SUPPLIER_ONLY
}
