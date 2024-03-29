/*
 * Created on 26 Apr 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    public TransportSegment getFirstSegment() {
        return segments.isEmpty() ? null : segments.get(0);
    }

    @JsonIgnore
    public TransportSegment getLastSegment() {
        return segments.isEmpty() ? null : segments.get(segments.size() - 1);
    }

    /**
     * Get departing destination of first segment
     *
     * @return
     */
    @JsonIgnore
    public Optional<Location> getDepartingDestination() {
        return Optional.ofNullable(getFirstSegment())
            .map(TransportSegment::getDepartingFromLocation);
    }

    /**
     * Get arrival destination of last segment
     *
     * @return
     */
    @JsonIgnore
    public Optional<Location> getArrivalDestination() {
        return Optional.ofNullable(getLastSegment())
            .map(TransportSegment::getArrivingAtLocation);
    }

    /**
     * Get destination at midpoint. For a two-segment leg, this is the destination
     * of the first segment
     *
     * @return
     */
    @JsonIgnore
    public Optional<Location> getMidpointDestination() {
        if (segments.isEmpty()) {
            return Optional.empty();
        }
        final int useArrivalOfSegmentIndex = (segments.size() - 1) / 2;
        return Optional.of(segments.get(useArrivalOfSegmentIndex).getArrivingAtLocation());
    }
}
