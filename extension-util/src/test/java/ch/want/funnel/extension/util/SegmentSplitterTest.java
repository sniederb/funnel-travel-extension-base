package ch.want.funnel.extension.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import ch.want.funnel.extension.model.SegmentedLeg;
import ch.want.funnel.extension.model.TransportSegment;

public class SegmentSplitterTest {

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SegmentArgumentsProvider.class)
    void split(@SuppressWarnings("unused") final String testName, final List<TransportSegment> segments, final String[] expectedLegArrivalDestinations) {
        final List<SegmentedLeg> legs = new SegmentSplitter(segments).split(8);
        // assert
        int segmentCount = 0;
        assertEquals(expectedLegArrivalDestinations.length, legs.size(), "Count of legs");
        for (int i = 0; i < expectedLegArrivalDestinations.length; i++) {
            assertEquals(expectedLegArrivalDestinations[i], legs.get(i).getArrivalDestination());
            segmentCount += legs.get(i).getSegments().size();
        }
        assertEquals(segments.size(), segmentCount, "Count of segments on all legs");
    }

    public static class SegmentArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    Arguments.of("zurichToFrankfurtAndBack", zurichToFrankfurtAndBack(), new String[] { "FRA/DE", "ZRH/CH" }),
                    Arguments.of("zurichToNewYorkViaFrankfurtAndBack", zurichToNewYorkViaFrankfurtAndBack(), new String[] { "JFK/US", "ZRH/CH" }),
                    Arguments.of("travelingSalesManToWarsawAndMoscowAndBelgrade", travelingSalesManToWarsawAndMoscowAndBelgrade(),
                            new String[] { "WAW/PL", "SVO/RU", "BEG/RS", "ZRH/CH" }),
                    Arguments.of("missingDates", missingDates(), new String[] { "ZRH/CH" }));
        }

        private List<TransportSegment> zurichToFrankfurtAndBack() {
            final List<TransportSegment> segments = new ArrayList<>();
            segments.add(buildSegment(LocalDateTime.parse("2019-10-15T10:30:00"), "ZRH/CH", LocalDateTime.parse("2019-10-15T11:25:00"), "FRA/DE", "2033"));
            segments.add(buildSegment(LocalDateTime.parse("2019-10-15T17:30:00"), "FRA/DE", LocalDateTime.parse("2019-10-15T18:25:00"), "ZRH/CH", "2032"));
            return segments;
        }

        private List<TransportSegment> zurichToNewYorkViaFrankfurtAndBack() {
            final List<TransportSegment> segments = new ArrayList<>();
            segments.add(buildSegment(LocalDateTime.parse("2019-10-15T10:30:00"), "ZRH/CH", LocalDateTime.parse("2019-10-15T11:25:00"), "FRA/DE", "2033"));
            segments.add(buildSegment(LocalDateTime.parse("2019-10-15T14:18:00"), "FRA/DE", LocalDateTime.parse("2019-10-16T08:45:00"), "JFK/US", "122"));
            segments.add(buildSegment(LocalDateTime.parse("2019-10-18T16:38:00"), "JFK/US", LocalDateTime.parse("2019-10-19T07:25:00"), "JFK/US", "128"));
            segments.add(buildSegment(LocalDateTime.parse("2019-10-19T09:30:00"), "FRA/DE", LocalDateTime.parse("2019-10-19T10:25:00"), "ZRH/CH", "2032"));
            return segments;
        }

        private List<TransportSegment> travelingSalesManToWarsawAndMoscowAndBelgrade() {
            final List<TransportSegment> segments = new ArrayList<>();
            segments.add(buildSegment(LocalDateTime.parse("2019-10-15T10:30:00"), "ZRH/CH", LocalDateTime.parse("2019-10-15T12:05:00"), "WAW/PL", "2033"));
            segments.add(buildSegment(LocalDateTime.parse("2019-10-16T08:18:00"), "WAW/PL", LocalDateTime.parse("2019-10-16T10:31:00"), "SVO/RU", "122"));
            segments.add(buildSegment(LocalDateTime.parse("2019-10-17T07:38:00"), "SVO/RU", LocalDateTime.parse("2019-10-17T07:25:00"), "BEG/RS", "128"));
            segments.add(buildSegment(LocalDateTime.parse("2019-10-18T09:30:00"), "BEG/RS", LocalDateTime.parse("2019-10-18T10:25:00"), "ZRH/CH", "2032"));
            return segments;
        }

        private List<TransportSegment> missingDates() {
            final List<TransportSegment> segments = new ArrayList<>();
            segments.add(buildSegment(LocalDateTime.parse("2019-10-15T10:30:00"), "ZRH/CH", null, "FRA/DE", "2033"));
            segments.add(buildSegment(LocalDateTime.parse("2019-10-15T14:18:00"), "FRA/DE", LocalDateTime.parse("2019-10-16T08:45:00"), "JFK/US", "122"));
            segments.add(buildSegment(null, "JFK/US", LocalDateTime.parse("2019-10-19T07:25:00"), "JFK/US", "128"));
            segments.add(buildSegment(LocalDateTime.parse("2019-10-19T09:30:00"), "FRA/DE", LocalDateTime.parse("2019-10-19T10:25:00"), "ZRH/CH", "2032"));
            return segments;
        }

        private TransportSegment buildSegment(final LocalDateTime departing, final String departingCode, final LocalDateTime arriving,
                final String arrivingCode, final String flightNumber) {
            final TransportSegment segment = new TransportSegment();
            segment.setDeparturetime(departing);
            segment.setDepartingfromdestination(departingCode);
            segment.setArrivaltime(arriving);
            segment.setArrivingatdestination(arrivingCode);
            segment.setConnectionnumber(flightNumber);
            return segment;
        }
    }
}
