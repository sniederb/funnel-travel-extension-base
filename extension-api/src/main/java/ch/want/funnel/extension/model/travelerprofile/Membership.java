package ch.want.funnel.extension.model.travelerprofile;

import java.io.Serializable;

import ch.want.funnel.extension.model.TravelServiceType;

public class Membership implements Serializable {

    private static final long serialVersionUID = 1L;
    private TravelServiceType travelType;
    private String providerCode;
    private String number;

    public TravelServiceType getTravelType() {
        return travelType;
    }

    public void setTravelType(final TravelServiceType travelType) {
        this.travelType = travelType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(final String providerCode) {
        this.providerCode = providerCode;
    }
}
