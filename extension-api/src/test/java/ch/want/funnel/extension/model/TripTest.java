package ch.want.funnel.extension.model;

import static org.hamcrest.MatcherAssert.*;

import org.apache.commons.io.IOUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class TripTest {

    private static final ObjectMapper OBJECTMAPPER;
    static {
        OBJECTMAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(new Jdk8Module())//
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void serialize() throws Exception {
        final byte[] payload = IOUtils.resourceToByteArray("/ch/want/funnel/extension/model/trip-7383012568246.json");
        // act
        final Trip trip = OBJECTMAPPER.readValue(payload, Trip.class);
        final String tripAsJson = OBJECTMAPPER.writeValueAsString(trip);
        //
        assertThat(tripAsJson, CoreMatchers.containsString("0742798225235"));
        assertThat(tripAsJson, CoreMatchers.containsString("header"));
    }
}
