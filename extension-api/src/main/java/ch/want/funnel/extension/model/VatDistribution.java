package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class VatDistribution implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private Double vatRate;
    private VatCategory category;
    private BigDecimal amount;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public VatCategory getCategory() {
        return category;
    }

    public void setCategory(final VatCategory category) {
        this.category = category;
    }

    /**
     * If data is available, the rate should be set by the provider extension. For known countries and a {@link #getCategory()}
     * unequal {@link VatCategory#EXEMPT}, funnel.travel will attempt to provide the correct rate.
     * A VAT of 7.7% is returned as 0.077.
     */
    public Double getVatRate() {
        return vatRate;
    }

    public void setVatRate(final Double vatRate) {
        this.vatRate = vatRate;
    }
}
