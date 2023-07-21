package ch.want.funnel.extension.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ch.want.funnel.extension.model.SegmentedLeg;
import ch.want.funnel.extension.model.TransportDocument;
import ch.want.funnel.extension.model.TransportSegment;
import ch.want.funnel.extension.model.TravelService;

class TransportSegmentUtilsTest {

    // static
    @Test
    void getAssociatedSegments() {
        final TravelService service = new TravelService();
        service.getSegmentedLegs().add(new SegmentedLeg());
        service.getSegmentedLegs().get(0).getSegments().add(new TransportSegment());
        service.getSegmentedLegs().get(0).getSegments().add(new TransportSegment());
        service.getSegmentedLegs().add(new SegmentedLeg());
        service.getSegmentedLegs().get(1).getSegments().add(new TransportSegment());
        service.getSegmentedLegs().get(1).getSegments().add(new TransportSegment());
        final TransportDocument document1 = new TransportDocument();
        document1.setAssociatedSegments(association(true, false, false, true));
        final TransportDocument document2 = new TransportDocument();
        document2.setAssociatedSegments(association(true, true, false, false));
        // act
        final List<TransportSegment> result = TransportSegmentUtils.getAssociatedSegments(service, document1, document2);
        // assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());
    }

    private List<String> association(final Boolean... values) {
        return Arrays.stream(values)
            .map(b -> b.toString())
            .collect(Collectors.toList());
    }
}
