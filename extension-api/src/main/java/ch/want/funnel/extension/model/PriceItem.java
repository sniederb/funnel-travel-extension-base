/*
 * Created on 26 Apr 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uuid")
public class PriceItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    @JsonBackReference("service-priceitems")
    private TravelService travelService;
    @JsonBackReference("document-prices")
    private TransportDocument transportDocument;
    private PriceItemType priceItemType;
    private String description;
    private String code;
    private String currency;
    private BigDecimal amount;
    private BigDecimal amountInAccountCurrency;
    private String purchasePriceCurrency;
    private BigDecimal purchasePrice;
    private BigDecimal purchasePriceInAccountCurrency;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public TravelService getTravelService() {
        return travelService;
    }

    public void setTravelService(final TravelService travelService) {
        this.travelService = travelService;
    }

    public TransportDocument getTransportDocument() {
        return transportDocument;
    }

    public void setTransportDocument(final TransportDocument transportDocument) {
        this.transportDocument = transportDocument;
    }

    public PriceItemType getPriceItemType() {
        return priceItemType;
    }

    public void setPriceItemType(final PriceItemType priceItemType) {
        this.priceItemType = priceItemType;
    }

    /**
     * varchar(1024) field. Leave empty if there is no descriptive text, use {@link #getPriceItemType()} to indicate the type of price item
     * (rather than setting descriptions like 'Fare' and 'Taxes'). For coded values, use {@link #getCode()}.
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * varchar(128) field. Beware that coded values are typically not displayed to the customer. Example:
     *
     * <pre>
     * Code: RO (for room only on a hotel booking)
     * </pre>
     */
    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
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

    /**
     * Get purchase price currency, with fallback to {@link #getCurrency()}
     */
    public String getPurchasePriceCurrency() {
        return purchasePriceCurrency == null ? currency : purchasePriceCurrency;
    }

    public void setPurchasePriceCurrency(final String purchasePriceCurrency) {
        this.purchasePriceCurrency = purchasePriceCurrency;
    }

    /**
     * Get the purchase amount in {@link #getPurchasePriceCurrency()}
     *
     * @return
     */
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(final BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * Get the purchase amount in the billing account's reference currency
     *
     * @return
     */
    public BigDecimal getPurchasePriceInAccountCurrency() {
        return purchasePriceInAccountCurrency;
    }

    public void setPurchasePriceInAccountCurrency(final BigDecimal purchasePriceInAccountCurrency) {
        this.purchasePriceInAccountCurrency = purchasePriceInAccountCurrency;
    }
}
