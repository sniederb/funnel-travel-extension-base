package ch.want.funnel.extension.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.want.funnel.extension.model.TransportDocument;
import ch.want.funnel.extension.model.TransportSegment;
import ch.want.funnel.extension.model.TravelService;

public class TransportSegmentUtils {

    private TransportSegmentUtils() {
    }

    public static List<TransportSegment> getAssociatedSegments(final TravelService flightService, final TransportDocument... documents) {
        final List<TransportSegment> travelSegments = flightService.getSegmentedLegs().stream()
            .flatMap(leg -> leg.getSegments().stream())
            .toList();
        final List<Boolean> segmentAssociations = new ArrayList<>();
        if (documents != null) {
            mergeSegmentAssociations(segmentAssociations, documents);
        }
        if (segmentAssociations.isEmpty()) {
            return travelSegments;
        }
        final List<TransportSegment> result = new ArrayList<>();
        for (int i = 0; (i < travelSegments.size()) && (i < segmentAssociations.size()); i++) {
            if (Boolean.TRUE.equals(segmentAssociations.get(i))) {
                result.add(travelSegments.get(i));
            }
        }
        return result;
    }

    private static void mergeSegmentAssociations(final List<Boolean> segmentAssociations, final TransportDocument... documents) {
        Arrays.stream(documents)
            .filter(doc -> !doc.getAssociatedSegments().isEmpty())
            .forEach(doc -> {
                for (int i = 0; i < doc.getAssociatedSegments().size(); i++) {
                    if ((i >= segmentAssociations.size()) || !Boolean.TRUE.equals(segmentAssociations.get(i))) {
                        setWithDynamicSize(segmentAssociations, i, Boolean.parseBoolean(doc.getAssociatedSegments().get(i)));
                    } // else: leave index at true, must've been set by other ticket
                }
            });
    }

    private static void setWithDynamicSize(final List<Boolean> segmentAssociations, final int index, final Boolean value) {
        while (segmentAssociations.size() <= index) {
            segmentAssociations.add(null);
        }
        segmentAssociations.set(index, value);
    }
}
