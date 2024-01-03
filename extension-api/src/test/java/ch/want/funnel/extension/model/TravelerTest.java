package ch.want.funnel.extension.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TravelerTest {

    @Test
    void passportCountryAlpha2() {
        final Traveler pax = new Traveler();
        pax.setPassportCountry("CH");
        Assertions.assertEquals("CH", pax.getPassportCountry());
    }

    @Test
    void passportCountryAlpha3() {
        final Traveler pax = new Traveler();
        pax.setPassportCountry("CHE");
        Assertions.assertEquals("CHE", pax.getPassportCountry());
    }

    @Test
    void passportCountrynonIso() {
        final Traveler pax = new Traveler();
        Assertions.assertThrows(IllegalArgumentException.class, () -> pax.setPassportCountry("Switzerland"));
    }
}
