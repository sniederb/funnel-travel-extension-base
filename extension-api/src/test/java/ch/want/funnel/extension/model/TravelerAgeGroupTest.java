package ch.want.funnel.extension.model;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TravelerAgeGroupTest {

    @ParameterizedTest
    @CsvSource({ "2022-01-05,2022-08-23,INF", "2019-01-05,2022-08-23,CHD", "2009-01-05,2022-08-23,ADT" })
    void fromBirthdate(final LocalDate birthDate, final LocalDate travelDate, final TravelerAgeGroup expected) {
        final TravelerAgeGroup actual = TravelerAgeGroup.fromBirthdate(birthDate, travelDate);
        Assertions.assertEquals(expected, actual);
    }
}
