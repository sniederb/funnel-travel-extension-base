package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class VatDistribution implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private String vatCode;
    private BigDecimal amount;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(final String vatCode) {
        this.vatCode = vatCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }
}
