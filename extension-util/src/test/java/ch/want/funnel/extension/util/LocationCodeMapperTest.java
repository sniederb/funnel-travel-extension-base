package ch.want.funnel.extension.util;

import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class LocationCodeMapperTest {

    @ParameterizedTest()
    @CsvSource({ "BENE,true", "BENERAIL,true", "SNCF,true", "Thalys,true", "DB,false", ",false" })
    void convertSncfToFunnelLocation(final String system, final boolean expectedResult) {
        final boolean result = LocationCodeMapper.isSncfGroup(system);
        Assertions.assertEquals(expectedResult, result);
    }

    @ParameterizedTest()
    @CsvSource({ "DEBBB,Basel Badischer Bahnhof", "BEBMI,Bruxelles-Midi", "FRGER,La Bresse", "FOOBAR," })
    void getBenerailRailStationName(final String stationCode, final String expectedResult) {
        final Optional<String> result = LocationCodeMapper.getBenerailRailStationName(stationCode);
        if (StringUtils.isBlank(expectedResult)) {
            Assertions.assertFalse(result.isPresent());
        } else {
            Assertions.assertTrue(result.isPresent());
            Assertions.assertEquals(expectedResult, result.get());
        }
    }

    @ParameterizedTest(name = "parseSncf_{0}")
    @MethodSource("createSncfTestdata")
    void convertSncfToFunnelLocation(final String sncfCode, final String expectedFunnelLocation) {
        final Optional<String> locationCode = LocationCodeMapper.convertSncfToFunnelLocation(sncfCode);
        if (expectedFunnelLocation != null) {
            Assertions.assertTrue(locationCode.isPresent());
            Assertions.assertEquals(expectedFunnelLocation, locationCode.get());
        } else {
            Assertions.assertFalse(locationCode.isPresent());
        }
    }

    @ParameterizedTest(name = "parseDeutscheBahn_{0}")
    @MethodSource("createDbTestdata")
    void convertDeutscheBahnToFunnelLocation(final String dbRl100Code, final String expectedFunnelLocation) {
        final Optional<String> locationCode = LocationCodeMapper.convertDeutscheBahnToFunnelLocation(dbRl100Code);
        if (expectedFunnelLocation != null) {
            Assertions.assertTrue(locationCode.isPresent());
            Assertions.assertEquals(expectedFunnelLocation, locationCode.get());
        } else {
            Assertions.assertFalse(locationCode.isPresent());
        }
    }

    @ParameterizedTest(name = "parseAmtrak_{0}")
    @MethodSource("createAmtrakTestdata")
    void convertAmtrakToFunnelLocation(final String amtrakCode, final String expectedFunnelLocation) {
        final Optional<String> locationCode = LocationCodeMapper.convertAmtrakToFunnelLocation(amtrakCode);
        if (expectedFunnelLocation != null) {
            Assertions.assertTrue(locationCode.isPresent());
            Assertions.assertEquals(expectedFunnelLocation, locationCode.get());
        } else {
            Assertions.assertFalse(locationCode.isPresent());
        }
    }

    public static Stream<Arguments> createSncfTestdata() {
        return Stream.of(
            Arguments.of("FRXSY", "SET/FR"),
            Arguments.of(null, null),
            Arguments.of("FRZZZ", null));
    }

    public static Stream<Arguments> createDbTestdata() {
        return Stream.of(
            Arguments.of("XSZH", "ZRH/CH"),
            Arguments.of("WH  W", "HAW/DE"),
            Arguments.of(null, null),
            Arguments.of("FRZZZ", null));
    }

    public static Stream<Arguments> createAmtrakTestdata() {
        return Stream.of(
            Arguments.of("NYP", "NYC/US"),
            Arguments.of(null, null),
            Arguments.of("XJK", null));
    }
}
