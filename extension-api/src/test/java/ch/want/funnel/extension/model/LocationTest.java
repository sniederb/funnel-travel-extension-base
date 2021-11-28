package ch.want.funnel.extension.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocationTest {

    @Test
    public void emptyLocationsAreEqual() {
        final Location locationA = new Location();
        final Location locationB = new Location();
        // act
        final boolean result = locationA.equals(locationB);
        // assert
        Assertions.assertTrue(result);
    }

    @Test
    public void sameCodesAreEqual() {
        final Location locationA = new Location();
        locationA.setIataCode("ZRH");
        locationA.setUnLocationCode("ZRH/CH");
        final Location locationB = new Location();
        locationB.setIataCode("ZRH");
        locationB.setUnLocationCode("ZRH/CH");
        // act
        final boolean result = locationA.equals(locationB);
        // assert
        Assertions.assertTrue(result);
    }

    @Test
    public void additionalCodesAreNotEqual() {
        final Location locationA = new Location();
        locationA.setIataCode("ZRH");
        locationA.setUnLocationCode("ZRH/CH");
        final Location locationB = new Location();
        locationB.setIataCode("ZRH");
        locationB.setUnLocationCode("ZRH/CH");
        locationB.setGeneralCode("CHAJD");
        // act
        final boolean result = locationA.equals(locationB);
        // assert
        Assertions.assertFalse(result);
    }

    @Test
    public void differentCodesAreNotEqual() {
        final Location locationA = new Location();
        locationA.setIataCode("ZRH");
        locationA.setUnLocationCode("ZRH/CH");
        final Location locationB = new Location();
        locationB.setIataCode("GVA");
        locationB.setUnLocationCode("GVA/CH");
        // act
        final boolean result = locationA.equals(locationB);
        // assert
        Assertions.assertFalse(result);
    }

    @Test
    public void toStringListsEntries() {
        final Location location = new Location();
        location.setIataCode("ZRH");
        location.setUnLocationCode("ZRH/CH");
        location.setCountryCode("CH");
        final String result = location.toString();
        Assertions.assertEquals("class ch.want.funnel.extension.model.Location{countryCode=CH, entries={IATA=ZRH, UNLOCATION=ZRH/CH}}", result);
    }
}
