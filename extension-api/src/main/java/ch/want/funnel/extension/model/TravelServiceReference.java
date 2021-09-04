package ch.want.funnel.extension.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class TravelServiceReference {

    @JsonBackReference("service-legs")
    private TravelService travelService;
    private String system;
    private String referenceNumber;
    private String agencyId;

    public TravelService getTravelService() {
        return travelService;
    }

    public void setTravelService(final TravelService travelService) {
        this.travelService = travelService;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(final String system) {
        this.system = system;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(final String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(final String agencyId) {
        this.agencyId = agencyId;
    }
}
