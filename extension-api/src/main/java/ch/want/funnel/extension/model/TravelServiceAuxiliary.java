package ch.want.funnel.extension.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class TravelServiceAuxiliary {

    @JsonBackReference("service-auxiliaries")
    private TravelService travelService;
    private String code;
    private String description;
    private String currency;
    private BigDecimal amount;

    public TravelService getTravelService() {
        return travelService;
    }

    public void setTravelService(final TravelService travelService) {
        this.travelService = travelService;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
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
}
