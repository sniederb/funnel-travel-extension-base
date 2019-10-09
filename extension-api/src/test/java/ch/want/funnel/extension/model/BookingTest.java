package ch.want.funnel.extension.model;

import static org.hamcrest.MatcherAssert.*;

import org.apache.commons.io.IOUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class BookingTest {

    @Test
    public void serialize() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        final byte[] payload = IOUtils.resourceToByteArray("/ch/want/funnel/extension/model/booking.json");
        // act
        final Booking booking = objectMapper.readValue(payload, Booking.class);
        final String bookingAsJson = objectMapper.writeValueAsString(booking);
        //
        assertThat(bookingAsJson, CoreMatchers.containsString("0742798225235"));
        assertThat(bookingAsJson, CoreMatchers.containsString("header"));
    }
}
