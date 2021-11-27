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
        locationA.set(Location.EntryType.IATA, "ZRH");
        locationA.set(Location.EntryType.UNLOCATION, "ZRH/CH");
        final Location locationB = new Location();
        locationB.set(Location.EntryType.IATA, "ZRH");
        locationB.set(Location.EntryType.UNLOCATION, "ZRH/CH");
        // act
        final boolean result = locationA.equals(locationB);
        // assert
        Assertions.assertTrue(result);
    }

    @Test
    public void additionalCodesAreNotEqual() {
        final Location locationA = new Location();
        locationA.set(Location.EntryType.IATA, "ZRH");
        locationA.set(Location.EntryType.UNLOCATION, "ZRH/CH");
        final Location locationB = new Location();
        locationB.set(Location.EntryType.IATA, "ZRH");
        locationB.set(Location.EntryType.UNLOCATION, "ZRH/CH");
        locationB.set(Location.EntryType.SNCF, "CHAJD");
        // act
        final boolean result = locationA.equals(locationB);
        // assert
        Assertions.assertFalse(result);
    }

    @Test
    public void differentCodesAreNotEqual() {
        final Location locationA = new Location();
        locationA.set(Location.EntryType.IATA, "ZRH");
        locationA.set(Location.EntryType.UNLOCATION, "ZRH/CH");
        final Location locationB = new Location();
        locationB.set(Location.EntryType.IATA, "GVA");
        locationB.set(Location.EntryType.UNLOCATION, "GVA/CH");
        // act
        final boolean result = locationA.equals(locationB);
        // assert
        Assertions.assertFalse(result);
    }

    @Test
    public void toStringListsEntries() {
        final Location location = new Location();
        location.set(Location.EntryType.IATA, "ZRH");
        location.set(Location.EntryType.UNLOCATION, "ZRH/CH");
        location.setCountryCode("CH");
        final String result = location.toString();
        Assertions.assertEquals("class ch.want.funnel.extension.model.Location{countryCode=CH, entries={IATA=ZRH, UNLOCATION=ZRH/CH}}", result);
    }
}
