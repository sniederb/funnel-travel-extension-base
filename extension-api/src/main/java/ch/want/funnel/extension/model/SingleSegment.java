/*
 * Created on 26 Apr 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.util.UUID;

public class SingleSegment implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UUID travelServiceUuid;
    private String destination;
    private String destinationName;
    private String providerDescription;
    private String serviceDescription;
    private String startConditions;
    private String endConditions;

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

    public String getDestination() {
        return destination;
    }

    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(final String destinationName) {
        this.destinationName = destinationName;
    }

    public String getProviderDescription() {
        return providerDescription;
    }

    public void setProviderDescription(final String providerDescription) {
        this.providerDescription = providerDescription;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(final String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getStartConditions() {
        return startConditions;
    }

    public void setStartConditions(final String startConditions) {
        this.startConditions = startConditions;
    }

    public String getEndConditions() {
        return endConditions;
    }

    public void setEndConditions(final String endConditions) {
        this.endConditions = endConditions;
    }
}
