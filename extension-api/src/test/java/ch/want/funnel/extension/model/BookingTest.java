package ch.want.funnel.extension.model;

import java.math.BigDecimal;
import java.util.TimeZone;

import org.apache.commons.io.IOUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class BookingTest {

    private static final ObjectMapper OBJECTMAPPER;
    static {
        // this matches the ObjectMapper as configured in funnel.travel
        OBJECTMAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(new Jdk8Module())
            .setTimeZone(TimeZone.getTimeZone("UTC"))
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
    }

    @Test
    void deserialize() throws Exception {
        final byte[] payload = IOUtils.resourceToByteArray("/ch/want/funnel/extension/model/booking-7383012568246.json");
        // act
        final Booking booking = OBJECTMAPPER.readValue(payload, Booking.class);
        final String bookingAsJson = OBJECTMAPPER.writeValueAsString(booking);
        //
        Assertions.assertEquals(2, booking.getTravelservices().size());
        final TravelService flightService = booking.getTravelservices().get(0);
        Assertions.assertEquals(2, flightService.getSegmentedLegs().size());
        final SegmentedLeg firstLeg = flightService.getSegmentedLegs().get(0);
        Assertions.assertEquals(2, firstLeg.getSegments().size());
        final TransportSegment firstSegment = firstLeg.getSegments().get(0);
        Assertions.assertNotNull(firstSegment.getDepartingFromLocation());
        Assertions.assertEquals("ZRH/CH", firstSegment.getDepartingFromLocation().getUnLocationCode());
        final TravelService hotelService = booking.getTravelservices().get(1);
        Assertions.assertNotNull(hotelService.getSingleSegment().getStartLocation());
        Assertions.assertEquals("Inverness", hotelService.getSingleSegment().getStartLocation().getName());
        MatcherAssert.assertThat(bookingAsJson, CoreMatchers.containsString("0742798225235"));
        MatcherAssert.assertThat(bookingAsJson, CoreMatchers.containsString("header"));
    }

    @Test
    void serialize() throws Exception {
        final Booking booking = ObjectFactory.createBooking();
        booking.getPriceitems().add(ObjectFactory.createPriceItem());
        final PriceItem onsitePrice = ObjectFactory.createPriceItem();
        onsitePrice.setPriceItemType(PriceItemType.ONSITE);
        booking.getTravelservices().get(0).getOnsitePriceitems().add(onsitePrice);
        // act
        final String bookingAsJson = OBJECTMAPPER.writeValueAsString(booking);
        //
        MatcherAssert.assertThat(bookingAsJson, CoreMatchers.containsString("iataCode"));
        MatcherAssert.assertThat(bookingAsJson, CoreMatchers.containsString("Mustermann"));
    }

    @Test
    void serializationRoundTrip() throws Exception {
        final Booking booking = ObjectFactory.createBooking();
        booking.getPriceitems().add(ObjectFactory.createPriceItem());
        final PriceItem onsitePrice = ObjectFactory.createPriceItem();
        onsitePrice.setPriceItemType(PriceItemType.ONSITE);
        booking.getTravelservices().get(0).getOnsitePriceitems().add(onsitePrice);
        final CustomFieldValue customFieldValue1 = ObjectFactory.createFieldValue("random.thought", "Value 1");
        booking.getTravelservices().get(0).getCustomfieldValues().add(customFieldValue1);
        booking.getTravelservices().get(0).getAssignedTravelers().add(booking.getParticipants().get(0));
        final CustomFieldValue customFieldValue2 = new CustomFieldValue();
        customFieldValue2.setCustomField(customFieldValue1.getCustomField());
        customFieldValue2.setInternalvalue("Value 2");
        booking.getTravelservices().get(1).getCustomfieldValues().add(customFieldValue2);
        // act
        final byte[] serialized = OBJECTMAPPER.writeValueAsBytes(booking);
        final Booking deserializedBooking = OBJECTMAPPER.readValue(serialized, Booking.class);
        // assert
        Assertions.assertEquals(booking.getTravelservices().size(), deserializedBooking.getTravelservices().size());
    }

    @Test
    void serializeWithCustomField() throws Exception {
        final Booking booking = ObjectFactory.createBooking();
        final CustomFieldValue customFieldValue = ObjectFactory.createFieldValue("invoiceNumber", "123456");
        final CustomfieldExtensionName extensionFieldName = new CustomfieldExtensionName();
        extensionFieldName.setCustomField(customFieldValue.getCustomField());
        extensionFieldName.setExtensionClassName("ch.want.WhateverExtension");
        extensionFieldName.setFieldName("invoiceNumber");
        customFieldValue.getCustomField().getExtensionFieldNames().add(extensionFieldName);
        booking.getCustomfieldValues().add(customFieldValue);
        // act
        final String bookingAsJson = OBJECTMAPPER.writeValueAsString(booking);
        //
        MatcherAssert.assertThat(bookingAsJson, CoreMatchers.containsString("invoiceNumber"));
        MatcherAssert.assertThat(bookingAsJson, CoreMatchers.containsString(extensionFieldName.getExtensionClassName()));
    }

    @Test
    void updateTotalpriceFromPriceitems() throws Exception {
        final byte[] payload = IOUtils.resourceToByteArray("/ch/want/funnel/extension/model/booking-7383012568246.json");
        // act
        final Booking booking = OBJECTMAPPER.readValue(payload, Booking.class);
        booking.updateTotalpriceFromPriceitems();
        //
        Assertions.assertEquals(new BigDecimal("1149.00"), booking.getTotalprice());
        Assertions.assertEquals("CHF", booking.getTotalpricecurrency());
    }
}
