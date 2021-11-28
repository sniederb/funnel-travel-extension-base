package ch.want.funnel.extension.util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ch.want.funnel.extension.model.Location;
import ch.want.funnel.extension.model.Trip;

public class DataUtilsTest {

    private final ObjectMapper objectMapper;

    public DataUtilsTest() {
        objectMapper = new ObjectMapper()//
            .registerModule(new JavaTimeModule())//
            .setTimeZone(TimeZone.getTimeZone("UTC"))
            .setSerializationInclusion(Include.NON_NULL)
            .enable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
    }

    @Test
    public void getCountry() throws Exception {
        final String country = DataUtils.getCountry("BCN/ES");
        assertEquals("ES", country);
    }

    @Test
    public void getDestination() throws Exception {
        final String destination = DataUtils.getDestination("BCN/ES");
        assertEquals("BCN", destination);
    }

    @Test
    public void getDepartingAirport() throws Exception {
        final Trip trip = getTrip();
        final Optional<Location> origin = DataUtils.getDepartingAirport(trip);
        assertTrue(origin.isPresent());
        assertEquals("ZRH/CH", origin.get().getUnLocationCode());
    }

    @TestFactory
    public List<DynamicTest> mapAssociationToNumbers() throws Exception {
        return Arrays.stream(new AssociationTestData[] { //
            new AssociationTestData("SimpleSingle", "1", new Integer[] { 1 }), //
            new AssociationTestData("ComprehensiveList", "1,2", new Integer[] { 1, 2 }), //
            new AssociationTestData("RangeList", "1,2-4", new Integer[] { 1, 2, 3, 4 }), //
            new AssociationTestData("MixedList", "1,2-4,7,9-11", new Integer[] { 1, 2, 3, 4, 7, 9, 10, 11 })//
        })
            .map(testdata -> DynamicTest.dynamicTest("Parse association " + testdata.description, () -> {
                final Collection<Integer> associations = DataUtils.mapAssociationToNumbers(testdata.input);
                assertThat(associations, containsInAnyOrder(testdata.expected));
            }))
            .collect(Collectors.toList());
    }

    private Trip getTrip() throws IOException {
        return objectMapper.readValue(this.getClass().getResourceAsStream("/example-trip-with-remarks.json"), Trip.class);
    }

    private static class AssociationTestData {

        private final String description;
        private final String input;
        private final Integer[] expected;

        private AssociationTestData(final String description, final String input, final Integer[] expected) {
            this.description = description;
            this.input = input;
            this.expected = expected;
        }
    }
}
