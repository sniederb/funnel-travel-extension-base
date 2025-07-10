package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <strong>Read-only</strong> class holding a form of payment used for future booking payments.
 */
public class BookingFormOfPayment implements Serializable {

    private static final long serialVersionUID = 1L;
    private String formOfPaymentType;
    private String identifier;
    private String identifierType;
    private LocalDate expiration;
    private String holderName;

    public String getFormOfPaymentType() {
        return formOfPaymentType;
    }

    public void setFormOfPaymentType(final String formOfPaymentType) {
        this.formOfPaymentType = formOfPaymentType;
    }

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
}
