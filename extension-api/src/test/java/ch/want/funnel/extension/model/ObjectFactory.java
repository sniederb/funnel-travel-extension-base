package ch.want.funnel.extension.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public final class ObjectFactory {

    private ObjectFactory() {
    }

    public static Booking createBooking() {
        final Booking booking = new Booking();
        booking.setCreated(OffsetDateTime.now());
        booking.setLastModified(OffsetDateTime.now());
        booking.setUuid(UUID.randomUUID());
        booking.getTravelservices().add(createTravelService(TravelServiceType.FLIGHT));
        booking.getTravelservices().add(createTravelService(TravelServiceType.HOTEL));
        booking.getParticipants().add(createTraveler());
        return booking;
    }

    public static TravelService createTravelService(final TravelServiceType type) {
        final TravelService service = new TravelService();
        service.setUuid(UUID.randomUUID());
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
        singleSegment.setUuid(UUID.randomUUID());
        singleSegment.setProviderDescription("Hotel Hilton");
        singleSegment.setStartLocation(createLocation("AMS", null, "Amsterdam"));
        singleSegment.setServiceDescription("Single room");
        return singleSegment;
    }

    public static SegmentedLeg createSegmentedLeg() {
        final SegmentedLeg leg = new SegmentedLeg();
        leg.setUuid(UUID.randomUUID());
        leg.getSegments().add(createTransportSegment());
        return leg;
    }

    public static TransportSegment createTransportSegment() {
        final TransportSegment segment = new TransportSegment();
        segment.setUuid(UUID.randomUUID());
        segment.setDeparturetime(LocalDateTime.now().plusHours(36));
        segment.setDepartingFromLocation(createLocation("ZRH", null, "Zurich"));
        segment.setArrivaltime(LocalDateTime.now().plusHours(38));
        segment.setArrivingAtLocation(createLocation("AMS", null, "Amsterdam"));
        return segment;
    }

    public static PriceItem createPriceItem() {
        final PriceItem priceItem = new PriceItem();
        priceItem.setUuid(UUID.randomUUID());
        priceItem.setPriceItemType(PriceItemType.DEFAULT);
        priceItem.setAmount(BigDecimal.TEN);
        priceItem.setCurrency("CHF");
        priceItem.setPurchasePrice(BigDecimal.TEN);
        priceItem.setPurchasePriceCurrency("CHF");
        return priceItem;
    }

    public static Traveler createTraveler() {
        final Traveler traveler = new Traveler();
        traveler.setUuid(UUID.randomUUID());
        traveler.setFirstname("Max");
        traveler.setLastname("Mustermann");
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

    public static CustomFieldValue createFieldValue(final String fieldname, final String value) {
        final CustomFieldValue fieldValue = new CustomFieldValue();
        fieldValue.setCustomField(createField(fieldname));
        fieldValue.setInternalvalue(value);
        return fieldValue;
    }

    private static CustomField createField(final String fieldname) {
        final CustomField field = new CustomField();
        field.setUuid(UUID.randomUUID());
        field.setName(fieldname);
        field.setDatatype(CustomFieldDataType.STRING);
        field.setEditDirective(CustomFieldDirective.OPTIONAL);
        return field;
    }
}
