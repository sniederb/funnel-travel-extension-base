package ch.want.funnel.extension.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TravelServiceReference {

    @JsonBackReference("references")
    private TravelService travelService;
    private String system;
    private String unmappedSystem;
    private String referenceNumber;
    private SystemReferenceType referenceType = SystemReferenceType.DEFAULT;
    private String agencyId;

    public TravelService getTravelService() {
        return travelService;
    }

    public void setTravelService(final TravelService travelService) {
        this.travelService = travelService;
    }

    /**
     * Get the reference system. For a modifier or consumer extension, note that provider mappings might have been applied.
     *
     * @return
     */
    public String getSystem() {
        return system;
    }

    public void setSystem(final String system) {
        this.system = system;
    }

    public void setSystem(final String system, final String unmappedSystem) {
        this.system = system;
        this.unmappedSystem = unmappedSystem;
    }

    /**
     * Get {@link #getSystem()} but without any provider mappings applied. If account or community have no provider mappings defined for
     * {@link #getUnmappedSystem()}, this method will return the same value as {@link #getSystem()}.
     */
    @JsonIgnore
    public String getUnmappedSystem() {
        return unmappedSystem == null ? system : unmappedSystem;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(final String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public SystemReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(final SystemReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(final String agencyId) {
        this.agencyId = agencyId;
    }
}
