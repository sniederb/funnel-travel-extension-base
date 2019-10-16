package ch.want.funnel.extension.util;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import ch.want.funnel.extension.model.SegmentedLeg;
import ch.want.funnel.extension.model.TransportSegment;

public class SegmentSplitter {

    private final List<TransportSegment> allsegments;

    public SegmentSplitter(final List<TransportSegment> allsegments) {
        this.allsegments = new ArrayList<>(allsegments);
        Collections.sort(this.allsegments);
    }

    /**
     * Any segment starting more than {@code maxHoursForLayover} will be split into a new leg.
     * If segments are missing departure/arrival time, they'll tend to be grouped into the same leg.
     *
     * Also, if a segment has the reverse departure/arrival codes of the previous segment, it is
     * grouped into a new leg (regardless of time)
     */
    public List<SegmentedLeg> split(final int maxHoursForLayover) {
        final List<SegmentedLeg> legs = new ArrayList<>();
        SegmentedLeg currentLeg = null;
        TransportSegment previousSegment = null;
        for (final TransportSegment segment : allsegments) {
            if (currentLeg == null) {
                currentLeg = new SegmentedLeg();
                currentLeg.setLegNr(legs.size() + 1);
                legs.add(currentLeg);
            } else if (previousSegment != null) {
                if (Objects.equals(previousSegment.getArrivingatdestination(), segment.getDepartingfromdestination()) &&
                        Objects.equals(previousSegment.getDepartingfromdestination(), segment.getArrivingatdestination())) {
                    currentLeg = new SegmentedLeg();
                    legs.add(currentLeg);
                } else if ((previousSegment.getArrivaltime() != null) && (segment.getDeparturetime() != null)) {
                    final long hours = previousSegment.getArrivaltime().until(segment.getDeparturetime(), ChronoUnit.HOURS);
                    if (hours > maxHoursForLayover) {
                        currentLeg = new SegmentedLeg();
                        legs.add(currentLeg);
                    }
                }
            }
            currentLeg.getSegments().add(segment);
            previousSegment = segment;
        }
        return legs;
    }
}
