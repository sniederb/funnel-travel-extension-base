/*
 * Created on 26 Apr 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uuid")
public class TransportDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    @JsonBackReference("service-documents")
    private TravelService travelService;
    private Traveler traveler;
    private TransportDocumentType transportDocumentType;
    private String referenceNumber;
    private String description;
    @JsonManagedReference("document-prices")
    private transient List<PriceItem> priceitems = new ArrayList<>();
    private transient List<String> associatedSegments = new ArrayList<>();

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

    public Traveler getTraveler() {
        return traveler;
    }

    public void setTraveler(final Traveler traveler) {
        this.traveler = traveler;
    }

    public TransportDocumentType getTransportDocumentType() {
        return transportDocumentType;
    }

    public void setTransportDocumentType(final TransportDocumentType transportDocumentType) {
        this.transportDocumentType = transportDocumentType;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(final String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Use {@link #getReferenceNumber()}
     * 
     * @return
     */
    @Deprecated
    public String getReferencenumber() {
        return getReferenceNumber();
    }

    /**
     * Use {@link #setReferenceNumber(String)}
     * 
     * @return
     */
    @Deprecated
    public void setReferencenumber(final String referencenumber) {
        setReferenceNumber(referencenumber);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<PriceItem> getPriceitems() {
        return priceitems;
    }

    public void setPriceitems(final List<PriceItem> priceitems) {
        this.priceitems = priceitems;
    }

    /**
     * Get a list of elements, the count of which must match the count of all segments present in the parent.
     * An association will be created/updated if the value at the matching index is truthy (true, TRUE), or if
     * the list contains the UUID of the segment (at any position!)
     */
    public List<String> getAssociatedSegments() {
        return associatedSegments;
    }

    public void initializeAssociatedSegments(final TravelService segmentedService) {
        initializeAssociatedSegments(segmentedService, false);
    }

    public void initializeAssociatedSegments(final TravelService segmentedService, final boolean defaultValue) {
        associatedSegments.clear();
        segmentedService.getSegmentedLegs().stream()
            .flatMap(leg -> leg.getSegments().stream())
            .forEach(seg -> associatedSegments.add(Boolean.toString(defaultValue)));
    }

    public void setAssociatedSegments(final List<String> associatedSegments) {
        this.associatedSegments = associatedSegments;
    }
}
