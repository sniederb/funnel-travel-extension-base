package ch.want.funnel.extension.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LocationTest {

    @Test
    public void isUndefined() {
        final Location location = new Location();
        Assertions.assertTrue(location.isUndefined());
        location.setIataCode("ZRH");
        Assertions.assertFalse(location.isUndefined());
    }

    @ParameterizedTest
    @CsvSource({ "Zürich,ZRH,ZRH/CH,Zürich", ",ZRH,ZRH/CH,ZRH", ",,ZRH/CH,ZRH/CH" })
    public void getGeneralDescription(final String name, final String iataCode, final String unLocationCode, final String expected) {
        final Location location = new Location();
        location.setName(name);
        location.setIataCode(iataCode);
        location.setUnLocationCode(unLocationCode);
        // act
        final String result = location.getGeneralDescription();
        // assert
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({ "CH,ZRH/CH,CH", ",ZRH/CH,CH", ",," })
    public void getCountryCode(final String countryCode, final String unLocationCode, final String expected) {
        final Location location = new Location();
        location.setUnLocationCode(unLocationCode);
        location.setCountryCode(countryCode);
        // act
        final String result = location.getCountryCode();
        // assert
        Assertions.assertEquals(expected, result);
    }
}
