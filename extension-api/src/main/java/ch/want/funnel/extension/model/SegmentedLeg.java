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
public class SegmentedLeg implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    @JsonBackReference("service-legs")
    private TravelService travelService;
    private Integer legNr;
    @JsonManagedReference("leg-segments")
    private transient List<TransportSegment> segments = new ArrayList<>();

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

    public Integer getLegNr() {
        return legNr;
    }

    public void setLegNr(final Integer legNr) {
        this.legNr = legNr;
    }

    public List<TransportSegment> getSegments() {
        return segments;
    }

    public void setSegments(final List<TransportSegment> segments) {
        this.segments = segments;
    }
}
