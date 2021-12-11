package ch.want.funnel.extension.model;

public enum TransportDocumentType {
    /**
     * Use for both original tickets and reissues. For the latter, be sure to populate the
     * {@link TransportDocument#getExchangeForReferenceNumber()}
     */
    TICKET,
    /**
     * Both EMD-A (EMD Associated) and EMD-S (EMD Stand-Alone) are mapped to this type.
     * Use {@link TransportDocument#isAssociated()} to distinguish between the two.
     */
    EMD
}
