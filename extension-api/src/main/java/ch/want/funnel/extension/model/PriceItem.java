/*
 * Created on 26 Apr 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class PriceItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UUID bookingUuid;
    private UUID travelServiceUuid;
    private UUID transportDocumentUuid;
    private PriceItemType priceItemType;
    private String description;
    private String currency;
    private BigDecimal amount;
    private BigDecimal amountInAccountCurrency;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getBookingUuid() {
        return bookingUuid;
    }

    public void setBookingUuid(final UUID bookingUuid) {
        this.bookingUuid = bookingUuid;
    }

    public UUID getTravelServiceUuid() {
        return travelServiceUuid;
    }

    public void setTravelServiceUuid(final UUID travelServiceUuid) {
        this.travelServiceUuid = travelServiceUuid;
    }

    public UUID getTransportDocumentUuid() {
        return transportDocumentUuid;
    }

    public void setTransportDocumentUuid(final UUID transportDocumentUuid) {
        this.transportDocumentUuid = transportDocumentUuid;
    }

    public PriceItemType getPriceItemType() {
        return priceItemType;
    }

    public void setPriceItemType(final PriceItemType priceItemType) {
        this.priceItemType = priceItemType;
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

    public BigDecimal getAmountInAccountCurrency() {
        return amountInAccountCurrency;
    }

    public void setAmountInAccountCurrency(final BigDecimal amountInAccountCurrency) {
        this.amountInAccountCurrency = amountInAccountCurrency;
    }
}
