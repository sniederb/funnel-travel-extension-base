package ch.want.funnel.extension.model;

import static org.hamcrest.MatcherAssert.*;

import org.apache.commons.io.IOUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TripTest {

    @Test
    public void serialize() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        final byte[] payload = IOUtils.resourceToByteArray("/ch/want/funnel/extension/model/trip.json");
        // act
        final Trip trip = objectMapper.readValue(payload, Trip.class);
        final String tripAsJson = objectMapper.writeValueAsString(trip);
        //
        assertThat(tripAsJson, CoreMatchers.containsString("0742798225235"));
        assertThat(tripAsJson, CoreMatchers.containsString("header"));
    }
}
