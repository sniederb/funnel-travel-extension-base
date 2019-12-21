package ch.want.funnel.extension.util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

public class DataUtilsTest {

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
