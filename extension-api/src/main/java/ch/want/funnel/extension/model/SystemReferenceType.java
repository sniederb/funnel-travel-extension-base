package ch.want.funnel.extension.model;

public enum SystemReferenceType {
    DEFAULT,
    /**
     * Indicates an internal reference from a producer source, such a Atriis or Nezasa
     */
    SOURCE,
    /**
     * Indicates a supplier reference. These are the ones typically used in an export to a midoffice
     */
    SUPPLIER,
    /**
     * A PNR-type reference, supplied by a GDS or similar
     */
    GDS
}
