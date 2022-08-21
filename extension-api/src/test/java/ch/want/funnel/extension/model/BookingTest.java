package ch.want.funnel.extension.model;

import java.math.BigDecimal;

import org.apache.commons.io.IOUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class BookingTest {

    private static final ObjectMapper OBJECTMAPPER;
    static {
        OBJECTMAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
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
        // act
        final String bookingAsJson = OBJECTMAPPER.writeValueAsString(booking);
        //
        MatcherAssert.assertThat(bookingAsJson, CoreMatchers.containsString("iataCode"));
        MatcherAssert.assertThat(bookingAsJson, CoreMatchers.containsString("Mustermann"));
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
