package ch.want.funnel.extension.util;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class LocationCodeMapperTest {

    @ParameterizedTest(name = "parse_{0}")
    @MethodSource("createTestdata")
    void convertSncfToFunnelLocation(final String sncfCode, final String expectedFunnelLocation) {
        final Optional<String> locationCode = LocationCodeMapper.convertSncfToFunnelLocation(sncfCode);
        if (expectedFunnelLocation != null) {
            Assertions.assertTrue(locationCode.isPresent());
            Assertions.assertEquals(expectedFunnelLocation, locationCode.get());
        } else {
            Assertions.assertFalse(locationCode.isPresent());
        }
    }

    public static Stream<Arguments> createTestdata() {
        return Stream.of(
            Arguments.of("FRXSY", "SET/FR"),
            Arguments.of(null, null),
            Arguments.of("FRZZZ", null));
    }
}
