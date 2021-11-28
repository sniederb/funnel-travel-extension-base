package ch.want.funnel.extension.model;

import java.time.LocalDateTime;

public final class ObjectFactory {

    private ObjectFactory() {
    }

    public static Booking createBooking() {
        final Booking booking = new Booking();
        booking.getTravelservices().add(createTravelService(TravelServiceType.FLIGHT));
        booking.getTravelservices().add(createTravelService(TravelServiceType.HOTEL));
        booking.getParticipants().add(createTraveler());
        return booking;
    }

    public static TravelService createTravelService(final TravelServiceType type) {
        final TravelService service = new TravelService();
        service.setTravelServiceType(type);
        if (TravelServiceType.hasSegments(type)) {
            service.setSingleSegment(createSingleSegment());
        } else {
            service.getSegmentedLegs().add(createSegmentedLeg());
        }
        return service;
    }

    public static SingleSegment createSingleSegment() {
        final SingleSegment singleSegment = new SingleSegment();
        singleSegment.setProviderDescription("Hotel Hilton");
        singleSegment.setStartLocation(createLocation("AMS", null, "Amsterdam"));
        singleSegment.setServiceDescription("Single room");
        return singleSegment;
    }

    public static SegmentedLeg createSegmentedLeg() {
        final SegmentedLeg leg = new SegmentedLeg();
        leg.getSegments().add(createTransportSegment());
        return leg;
    }

    public static TransportSegment createTransportSegment() {
        final TransportSegment segment = new TransportSegment();
        segment.setDeparturetime(LocalDateTime.now().plusHours(36));
        segment.setDepartingFromLocation(createLocation("ZRH", null, "Zurich"));
        segment.setArrivaltime(LocalDateTime.now().plusHours(38));
        segment.setArrivingAtLocation(createLocation("AMS", null, "Amsterdam"));
        return segment;
    }

    public static Traveler createTraveler() {
        final Traveler traveler = new Traveler();
        traveler.setFirstname("Max");
        traveler.setFirstname("Mustermann");
        return traveler;
    }

    public static Location createLocation(final String iataCode, final String unLocation, final String general) {
        final Location location = new Location();
        if (iataCode != null) {
            location.setIataCode(iataCode);
        }
        if (unLocation != null) {
            location.setUnLocationCode(unLocation);
        }
        if (general != null) {
            location.setName(general);
        }
        return location;
    }
}
