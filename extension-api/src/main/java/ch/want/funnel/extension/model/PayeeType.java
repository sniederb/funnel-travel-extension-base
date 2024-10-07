package ch.want.funnel.extension.model;

public enum PayeeType {
    /**
     * Payee type is unknown.
     */
    UNKNOWN,
    /**
     * Payment was done by the agency, thus the customer still owes the agency some money.
     */
    AGENCY,
    /**
     * Payment was done by the customer directly.
     */
    CUSTOMER
}
