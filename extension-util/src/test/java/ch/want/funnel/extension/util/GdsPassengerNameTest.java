package ch.want.funnel.extension.util;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import ch.want.funnel.extension.model.TravelerAgeGroup;

class GdsPassengerNameTest {

    @ParameterizedTest(name = "parse_{0}")
    @MethodSource("createTestdata")
    void ctor(final String gdsName, final String expectedLastname, final String expectedFirstname, final String expectedSalutation,
            final String expectedTitle) {
        final GdsPassengerName testee = new GdsPassengerName(gdsName);
        Assertions.assertEquals(expectedLastname, testee.getLastname());
        Assertions.assertEquals(expectedFirstname, testee.getFirstname());
        Assertions.assertEquals(expectedSalutation, testee.getSalutation());
        Assertions.assertEquals(expectedTitle, testee.getTitle());
    }

    public static Stream<Arguments> createTestdata() {
        return Stream.of(
            Arguments.of("NIEDERBERGER/SIMON MR", "NIEDERBERGER", "SIMON", "MR", ""),
            Arguments.of("NIEDERBERGER/SIMONE MRS(ID234567)", "NIEDERBERGER", "SIMONE", "MRS", ""),
            Arguments.of("NIEDERBERGER/SIMON DR MR", "NIEDERBERGER", "SIMON", "MR", "DR"),
            Arguments.of("NIEDERBERGER/SIMON PROF MR", "NIEDERBERGER", "SIMON", "MR", "PROF"),
            Arguments.of("NIEDERBERGER/SIMONMR", "NIEDERBERGER", "SIMON", "MR", ""),
            Arguments.of("DOE/JOHN ALEXANDER MR", "DOE", "JOHN ALEXANDER", "MR", ""),
            Arguments.of("SIERRA/THALIA(CHD)", "SIERRA", "THALIA", "", ""),
            Arguments.of("STRUMPF/MARGE MRS (INF/LENNY/04OCT10))", "STRUMPF", "MARGE", "MRS", ""),
            Arguments.of("CARRINGTON/ALEXISMRS(ADT)(ID234567)", "CARRINGTON", "ALEXIS", "MRS", ""));
    }

    @Test
    void parseAssociatedInfant() {
        final GdsPassengerName testee = new GdsPassengerName("STRUMPF/MARGE MRS (INF/LENNY/04OCT10)");
        Assertions.assertEquals("STRUMPF", testee.getLastname());
        Assertions.assertEquals(TravelerAgeGroup.ADT, testee.getPassengerType());
        Assertions.assertEquals("LENNY", testee.getInfantName());
        Assertions.assertNotNull(testee.getInfantBirthdate());
        Assertions.assertEquals("2010-10-04", testee.getInfantBirthdate().toString());
    }

    @Test
    void parseInfant() {
        final GdsPassengerName testee = new GdsPassengerName("STRUMPF/LENNY(INF)");
        Assertions.assertEquals("STRUMPF", testee.getLastname());
        Assertions.assertEquals("LENNY", testee.getFirstname());
        Assertions.assertEquals(TravelerAgeGroup.INF, testee.getPassengerType());
    }
}
