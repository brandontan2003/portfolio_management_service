package com.example.portfolio.service.enums;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.example.portfolio.service.enums.LocationTypeEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class LocationTypeEnumTest {
    static Stream<Arguments> test_locationTypeEnum() {
        return Stream.of(
                Arguments.of(HYBRID, HYBRID.getValue()),
                Arguments.of(REMOTE, REMOTE.getValue()),
                Arguments.of(ON_SITE, ON_SITE.getValue())
        );
    }

    @ParameterizedTest
    @MethodSource("test_locationTypeEnum")
    void test_locationTypeEnum(LocationTypeEnum expectedValue, String actualValue) {
        assertEquals(expectedValue, LocationTypeEnum.valueOf(actualValue));
    }
}
