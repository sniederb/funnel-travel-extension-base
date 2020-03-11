/*
 * Created on 26 Apr 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class SingleSegment implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    @JsonBackReference("singlesegment")
    private TravelService travelService;
    private String destination;
    private String destinationName;
    private String providerDescription;
    private String serviceDescription;
    private String startConditions;
    private String endConditions;
    private String street;
    private String zip;
    private String city;

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

    public String getDestination() {
        return destination;
    }

    /**
     * Set the destination in a best-effort:
     * <ul>
     * <li>If available, set &lt;three-letter location code&gt;/&lt;two-letter country code&gt; (eg ZRH/CH)</li>
     * <li>If a destination description is available, set that</li>
     * <li>If a two-letter country code is available, add it with a trailing slash (eg. Heathrow/GB)</li>
     * </ul>
     * 
     * @param destination
     */
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

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }
}
