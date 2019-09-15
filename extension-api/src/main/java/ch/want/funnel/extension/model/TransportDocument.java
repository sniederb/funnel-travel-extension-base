/*
 * Created on 26 Apr 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransportDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UUID travelServiceUuid;
    private UUID travellerUuid;
    private TransportDocumentType transportDocumentType;
    private String referencenumber;
    private String description;
    private transient List<PriceItem> priceitems = new ArrayList<>();
    private transient List<String> associatedSegments = new ArrayList<>();

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getTravelServiceUuid() {
        return travelServiceUuid;
    }

    public void setTravelServiceUuid(final UUID travelServiceUuid) {
        this.travelServiceUuid = travelServiceUuid;
    }

    public UUID getTravellerUuid() {
        return travellerUuid;
    }

    public void setTravellerUuid(final UUID travellerUuid) {
        this.travellerUuid = travellerUuid;
    }

    public TransportDocumentType getTransportDocumentType() {
        return transportDocumentType;
    }

    public void setTransportDocumentType(final TransportDocumentType transportDocumentType) {
        this.transportDocumentType = transportDocumentType;
    }

    public String getReferencenumber() {
        return referencenumber;
    }

    public void setReferencenumber(final String referencenumber) {
        this.referencenumber = referencenumber;
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

    public List<String> getAssociatedSegments() {
        return associatedSegments;
    }

    public void setAssociatedSegments(final List<String> associatedSegments) {
        this.associatedSegments = associatedSegments;
    }
}
