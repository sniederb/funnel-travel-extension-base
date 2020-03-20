package ch.want.funnel.extension.model.travelerprofile;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((travelType == null) ? 0 : travelType.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Membership other = (Membership) obj;
        return Objects.equals(travelType, other.travelType) && Objects.equals(providerCode, other.providerCode);
    }
}
