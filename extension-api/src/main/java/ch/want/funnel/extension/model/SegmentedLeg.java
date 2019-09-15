/*
 * Created on 26 Apr 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SegmentedLeg implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UUID travelServiceUuid;
    private Integer legNr;
    private transient List<TransportSegment> segments = new ArrayList<>();

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
