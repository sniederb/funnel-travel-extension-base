package ch.want.funnel.extension.model;

/**
 * This enum lists payment processors and/or acquirers.
 */
public enum FormOfPaymentSystem {
    /**
     * https://www.datatrans.ch/
     */
    DATATRANS,
    /**
     * Fallback entry
     */
    OTHER,
    /**
     * https://www.computop.com/, owned by Nexi
     */
    COMPUTOP,
    /**
     * https://www.worldpay.com/
     */
    WORLDPAY,
    /**
     * https://www.stripe.com/
     */
    STRIPE,
    /**
     * https://www.paypal.com/
     */
    PAYPAL,
    /**
     * https://www.adyen.com/
     */
    ADYEN
}
