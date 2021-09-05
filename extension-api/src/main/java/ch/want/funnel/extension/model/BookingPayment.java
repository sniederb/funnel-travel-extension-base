/*
 * Created on 26 Apr 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BookingPayment implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private OffsetDateTime entryTimestamp;
    private String description;
    private String serviceReference;
    private String currency;
    private BigDecimal amount;
    private PaymentType paymentType;
    private String ccNumber;
    private LocalDate ccExpiration;

    public BookingPayment() {
        // default c'tor
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public OffsetDateTime getEntryTimestamp() {
        return entryTimestamp;
    }

    public void setEntryTimestamp(final OffsetDateTime entryTimestamp) {
        this.entryTimestamp = entryTimestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(final PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(final String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public LocalDate getCcExpiration() {
        return ccExpiration;
    }

    public void setCcExpiration(final LocalDate ccExpiration) {
        this.ccExpiration = ccExpiration;
    }

    public String getServiceReference() {
        return serviceReference;
    }

    public void setServiceReference(final String serviceReference) {
        this.serviceReference = serviceReference;
    }
}
