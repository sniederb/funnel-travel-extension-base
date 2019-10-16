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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uuid")
@JsonIgnoreProperties(value = { "departingDestination", "departingDestinationName", "arrivalDestination", "arrivalDestinationName",
        "midpointDestination" }, allowGetters = true)
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

    /**
     * Get departing destination of first segment
     *
     * @return
     */
    public String getDepartingDestination() {
        if (segments.isEmpty()) {
            return null;
        }
        return segments.get(0).getDepartingfromdestination();
    }

    public String getDepartingDestinationName() {
        if (segments.isEmpty()) {
            return null;
        }
        return segments.get(0).getDepartingFromDestinationName();
    }

    /**
     * Get arrival destination of last segment
     *
     * @return
     */
    public String getArrivalDestination() {
        if (segments.isEmpty()) {
            return null;
        }
        return segments.get(segments.size() - 1).getArrivingatdestination();
    }

    public String getArrivalDestinationName() {
        if (segments.isEmpty()) {
            return null;
        }
        return segments.get(segments.size() - 1).getArrivingAtDestinationName();
    }

    /**
     * Get destination at midpoint. For a two-segment leg, this is the destination
     * of the first segment
     *
     * @return
     */
    public String getMidpointDestination() {
        if (segments.isEmpty()) {
            return null;
        }
        final int useArrivalOfSegmentIndex = (segments.size() - 1) / 2;
        return segments.get(useArrivalOfSegmentIndex).getArrivingatdestination();
    }
}
