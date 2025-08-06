package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Class holding a form of payment used for future booking payments.
 */
public class BookingFormOfPayment implements Serializable {

    private static final long serialVersionUID = 1L;
    private String transactionNumber;
    private FormOfPaymentSystem system;
    private String formOfPaymentType;
    private String identifier;
    private String identifierType;
    private LocalDate expiration;
    private String holderName;

    /**
     * Get the payment processing system.
     *
     * @return
     */
    public FormOfPaymentSystem getSystem() {
        return system;
    }

    public void setSystem(final FormOfPaymentSystem system) {
        this.system = system;
    }

    /**
     * Get the form of payment (such as 'VI' or 'ECA')
     *
     * @return
     */
    public String getFormOfPaymentType() {
        return formOfPaymentType;
    }

    public void setFormOfPaymentType(final String formOfPaymentType) {
        this.formOfPaymentType = formOfPaymentType;
    }

    /**
     * Get the form of payment identifier, typically an account or a tokenized credit card number.
     *
     * @return
     */
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(final String identifierType) {
        this.identifierType = identifierType;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public void setExpiration(final LocalDate expiration) {
        this.expiration = expiration;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(final String holderName) {
        this.holderName = holderName;
    }

    /**
     * Get the transaction identifier associated with the payment transaction.
     */
    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(final String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }
}
