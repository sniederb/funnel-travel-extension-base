package ch.want.funnel.extension.model;

public enum TransportDocumentType {
    TICKET, //
    /**
     * Both EMD-A (EMD Associated) and EMD-S (EMD Stand-Alone) are mapped to this type.
     * If the {@link TransportDocument} has associated segments, it'll be an EMD-A.
     */
    EMD
}
