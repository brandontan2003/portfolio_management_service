package com.example.portfolio.service.enums;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.example.portfolio.service.enums.ProjectStatusEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProjectStatusEnumTest {
    static Stream<Arguments> test_projectStatusEnum() {
        return Stream.of(
                Arguments.of(COMPLETED, COMPLETED.getValue()),
                Arguments.of(ON_HOLD, ON_HOLD.getValue()),
                Arguments.of(IN_PROGRESS, IN_PROGRESS.getValue())
        );
    }

    @ParameterizedTest
    @MethodSource("test_projectStatusEnum")
    void test_projectStatusEnum(ProjectStatusEnum expectedValue, String actualValue) {
        assertEquals(expectedValue, ProjectStatusEnum.valueOf(actualValue));
    }
}
