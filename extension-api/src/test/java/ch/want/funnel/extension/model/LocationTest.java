package ch.want.funnel.extension.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LocationTest {

    @Test
    void isUndefined() {
        final Location location = new Location();
        Assertions.assertTrue(location.isUndefined());
        location.setIataCode("ZRH");
        Assertions.assertFalse(location.isUndefined());
    }

    @ParameterizedTest
    @CsvSource({ "Zürich,ZRH,ZRH/CH,Zürich", ",ZRH,ZRH/CH,ZRH", ",,ZRH/CH,ZRH/CH" })
    void getGeneralDescription(final String name, final String iataCode, final String unLocationCode, final String expected) {
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
    void getCountryCode(final String countryCode, final String unLocationCode, final String expected) {
        final Location location = new Location();
        location.setUnLocationCode(unLocationCode);
        location.setCountryCode(countryCode);
        // act
        final String result = location.getCountryCode();
        // assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    void equalsOnSameIataCode() {
        final Location locationA = createWithIataCode("LHR");
        final Location locationB = createWithIataCode("LHR");
        // act
        final boolean result = locationA.equals(locationB);
        // assert
        Assertions.assertTrue(result);
    }

    @Test
    void equalsOnDifferentIataCode() {
        final Location locationA = createWithIataCode("LHR");
        final Location locationB = createWithIataCode("INV");
        // act
        final boolean result = locationA.equals(locationB);
        // assert
        Assertions.assertFalse(result);
    }

    @Test
    void equalsOnUnLocationCode() {
        final Location locationA = createWithUnLocation("LHR/GB");
        final Location locationB = createWithUnLocation("LHR/GB");
        // act
        final boolean result = locationA.equals(locationB);
        // assert
        Assertions.assertTrue(result);
    }

    @Test
    void equalsOnSameName() {
        final Location locationA = createWithName("Inverness", "GB");
        final Location locationB = createWithName("Inverness", "GB");
        // act
        final boolean result = locationA.equals(locationB);
        // assert
        Assertions.assertTrue(result);
    }

    @Test
    void equalsOnDifferentName() {
        final Location locationA = createWithName("Inverness", "GB");
        final Location locationB = createWithName("London", "GB");
        // act
        final boolean result = locationA.equals(locationB);
        // assert
        Assertions.assertFalse(result);
    }

    @Test
    void equalsOnEmptyStrings() {
        final Location locationA = createWithName("Inverness", "GB");
        locationA.setUnLocationCode("");
        locationA.setGeneralCode("");
        final Location locationB = createWithName("London", "GB");
        locationB.setUnLocationCode("");
        locationB.setGeneralCode("");
        // act
        final boolean result = locationA.equals(locationB);
        // assert
        Assertions.assertFalse(result);
    }

    private static Location createWithIataCode(final String iataCode) {
        final Location location = new Location();
        location.setIataCode(iataCode);
        return location;
    }

    private static Location createWithUnLocation(final String unLocationCode) {
        final Location location = new Location();
        location.setUnLocationCode(unLocationCode);
        return location;
    }

    private static Location createWithName(final String name, final String countryCode) {
        final Location location = new Location();
        location.setName(name);
        location.setCountryCode(countryCode);
        return location;
    }
}
