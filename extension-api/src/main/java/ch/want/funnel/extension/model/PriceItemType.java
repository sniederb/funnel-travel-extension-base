package ch.want.funnel.extension.model;

public enum PriceItemType {
    DEFAULT, //
    /**
     * Flight or train fare. Priceitems of this type should be attached to a {@link TransportDocument}, which itself has a
     * {@link TransportDocument#getTraveler()} set
     */
    FARE_PER_PAX, //
    /**
     * Flight tax. Priceitems of this type should be attached to a {@link TransportDocument}, which itself has a
     * {@link TransportDocument#getTraveler()} set
     */
    TAX_PER_PAX, //
    /**
     * 'Off ticket' fees (OB/OC/OD fees). Priceitems of this type should be attached to a {@link TransportDocument}, which itself has a
     * {@link TransportDocument#getTraveler()} set
     */
    OFF_TICKET_PER_PAX, //
    /**
     * General-purpose pricing, but unlike {@link #DEFAULT} this is per pax. Priceitems of this type should be attached to a
     * {@link TransportDocument}, which itself has a {@link TransportDocument#getTraveler()} set
     */
    AUX_PER_PAX, //
    AUX_PER_BOOKING, //
    /**
     * Booking fee. Priceitems of this type attached to a {@link TransportDocument} are often merged with {@link #FARE_PER_PAX} by the
     * consumer.
     */
    FEE_PER_PAX
}
