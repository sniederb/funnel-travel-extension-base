package ch.want.funnel.extension.util.sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ch.want.funnel.extension.model.SegmentedLeg;
import ch.want.funnel.extension.model.SingleSegment;
import ch.want.funnel.extension.model.TransportSegment;
import ch.want.funnel.extension.model.TravelService;
import ch.want.funnel.extension.model.TravelServiceType;

class TravelServiceSortTest {

    @Test
    void sortSimple() {
        final List<TravelService> services = new ArrayList<>();
        services.add(flight("FLT", LocalDateTime.parse("2025-05-19T13:15:00"), "FRA"));
        services.add(hotel("HTL", LocalDate.parse("2025-05-19"), "afternoon", "FRA"));
        services.add(transfer("TRF", LocalDate.parse("2025-05-19"), "", null));
        services.add(transfer("TRF2", LocalDate.parse("2025-05-22"), "", null));
        final TravelServiceSort<TravelService> testee = new TravelServiceSort<>(new DefaultTravelServiceSortKeyTranslator());
        // act
        final List<TravelService> result = testee.sort(services);
        // assert
        Assertions.assertEquals("FLT", result.get(0).getReferenceNumber());
        Assertions.assertEquals("TRF", result.get(1).getReferenceNumber());
        Assertions.assertEquals("HTL", result.get(2).getReferenceNumber());
        Assertions.assertEquals("TRF2", result.get(3).getReferenceNumber());
    }

    @Test
    void sortMultiTransfer() {
        final List<TravelService> services = new ArrayList<>();
        services.add(flight("19-FL", LocalDateTime.parse("2025-05-19T13:15:00"), "FRA"));
        services.add(hotel("19-HTL", LocalDate.parse("2025-05-19"), "afternoon", "FRA"));
        services.add(transfer("19-TR", LocalDate.parse("2025-05-19"), "", null));
        services.add(activity("19-EV", LocalDate.parse("2025-05-19"), "", null));
        services.add(hotel("22-HTL", LocalDate.parse("2025-05-22"), "03:00 PM", "JFK"));
        services.add(flight("22-FL", LocalDateTime.parse("2025-05-22T09:22:00"), "JFK"));
        services.add(transfer("22-TR1", LocalDate.parse("2025-05-22"), "7:15", null));
        services.add(transfer("22-TR2", LocalDate.parse("2025-05-22"), "14:30", null));
        final TravelServiceSort<TravelService> testee = new TravelServiceSort<>(new DefaultTravelServiceSortKeyTranslator());
        // act
        final List<TravelService> result = testee.sort(services);
        // assert
        Assertions.assertEquals("19-FL", result.get(0).getReferenceNumber());
        Assertions.assertEquals("19-TR", result.get(1).getReferenceNumber());
        Assertions.assertEquals("19-EV", result.get(2).getReferenceNumber());
        Assertions.assertEquals("19-HTL", result.get(3).getReferenceNumber());
        Assertions.assertEquals("22-TR1", result.get(4).getReferenceNumber());
        Assertions.assertEquals("22-FL", result.get(5).getReferenceNumber());
        Assertions.assertEquals("22-TR2", result.get(6).getReferenceNumber());
        Assertions.assertEquals("22-HTL", result.get(7).getReferenceNumber());
    }

    private TravelService flight(final String refNumber, final LocalDateTime departure, final String iataCode) {
        final TravelService flight = new TravelService();
        flight.setUuid(UUID.randomUUID());
        flight.setTravelServiceType(TravelServiceType.FLIGHT);
        flight.setReferenceNumber(refNumber);
        flight.getSegmentedLegs().add(new SegmentedLeg());
        flight.getSegmentedLegs().get(0).getSegments().add(new TransportSegment());
        final TransportSegment firstSegment = flight.getSegmentedLegs().get(0).getSegments().get(0);
        firstSegment.getDepartingFromLocation().setIataCode(iataCode);
        firstSegment.setDeparturetime(departure);
        flight.setDeparturedate(departure.toLocalDate());
        return flight;
    }

    private TravelService hotel(final String refNumber, final LocalDate checkin, final String timeExpression, final String iataCode) {
        final TravelService hotel = new TravelService();
        hotel.setUuid(UUID.randomUUID());
        hotel.setTravelServiceType(TravelServiceType.HOTEL);
        hotel.setSingleSegment(new SingleSegment());
        hotel.setReferenceNumber(refNumber);
        hotel.setDeparturedate(checkin);
        hotel.getSingleSegment().setStartTime(timeExpression);
        hotel.getSingleSegment().getStartLocation().setIataCode(iataCode);
        return hotel;
    }

    private TravelService transfer(final String refNumber, final LocalDate checkin, final String timeExpression, final String iataCode) {
        final TravelService transfer = new TravelService();
        transfer.setUuid(UUID.randomUUID());
        transfer.setTravelServiceType(TravelServiceType.MISC);
        transfer.setSingleSegment(new SingleSegment());
        transfer.getSingleSegment().setTransfer(true);
        transfer.setReferenceNumber(refNumber);
        transfer.setDeparturedate(checkin);
        transfer.getSingleSegment().setStartTime(timeExpression);
        transfer.getSingleSegment().getStartLocation().setIataCode(iataCode);
        return transfer;
    }

    private TravelService activity(final String refNumber, final LocalDate checkin, final String timeExpression, final String iataCode) {
        final TravelService activity = new TravelService();
        activity.setUuid(UUID.randomUUID());
        activity.setTravelServiceType(TravelServiceType.MISC);
        activity.setSingleSegment(new SingleSegment());
        activity.getSingleSegment().setEvent(true);
        activity.setReferenceNumber(refNumber);
        activity.setDeparturedate(checkin);
        activity.getSingleSegment().setStartTime(timeExpression);
        activity.getSingleSegment().getStartLocation().setIataCode(iataCode);
        return activity;
    }
}
