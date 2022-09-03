package ch.want.funnel.extension.model;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AgeGroupTest {

    @ParameterizedTest
    @CsvSource({ "2022-01-05,2022-08-23,INFANT", "2019-01-05,2022-08-23,CHILD", "2009-01-05,2022-08-23,ADULT" })
    void fromBirthdate(final LocalDate birthDate, final LocalDate travelDate, final AgeGroup expected) {
        final AgeGroup actual = AgeGroup.fromBirthdate(birthDate, travelDate);
        Assertions.assertEquals(expected, actual);
    }
}
