package ch.want.funnel.extension.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Optional;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ch.want.funnel.extension.model.Trip;

public class ExtensionDataAccessTest {

    private final ObjectMapper objectMapper;

    public ExtensionDataAccessTest() {
        objectMapper = new ObjectMapper()//
            .registerModule(new JavaTimeModule())//
            .setTimeZone(TimeZone.getTimeZone("UTC"))
            .setSerializationInclusion(Include.NON_NULL)
            .enable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
    }

    @Test
    public void searchAllExtensionDataForRemark() throws Exception {
        final Trip trip = getTrip();
        final Optional<String> result = ExtensionDataAccess.searchAllExtensionDataForRemark(trip, "VISA/N-");
        assertTrue(result.isPresent());
        assertEquals("VISA/N-DE", result.get());
    }

    @Test
    public void searchAllExtensionDataForRemark_withTattoo() throws Exception {
        final Trip trip = getTrip();
        final Optional<String> result = ExtensionDataAccess.searchAllExtensionDataForRemark(trip, "VISA/E-", "1");
        assertTrue(result.isPresent());
        assertEquals("VISA/E-FOO.BAR@NOWHERE.COM", result.get());
    }

    @Test
    public void searchAllExtensionDataForRemark_withNonNumericTattoo() throws Exception {
        final Trip trip = getTrip();
        final Optional<String> result = ExtensionDataAccess.searchAllExtensionDataForRemark(trip, "VISA/E-", "P1");
        assertFalse(result.isPresent());
    }

    @Test
    public void searchAllExtensionDataForRemark_withNullTattooOnRestrictedEntry() throws Exception {
        final Trip trip = getTrip();
        final Optional<String> result = ExtensionDataAccess.searchAllExtensionDataForRemark(trip, "VISA/E-", null);
        assertFalse(result.isPresent());
    }

    @Test
    public void searchAllExtensionDataForRemark_withNullTattooOnUnrestrictedEntry() throws Exception {
        final Trip trip = getTrip();
        final Optional<String> result = ExtensionDataAccess.searchAllExtensionDataForRemark(trip, "VISA/N-", null);
        assertTrue(result.isPresent());
        assertEquals("VISA/N-DE", result.get());
    }

    private Trip getTrip() throws IOException {
        return objectMapper.readValue(this.getClass().getResourceAsStream("/example-trip-with-remarks.json"), Trip.class);
    }
}
