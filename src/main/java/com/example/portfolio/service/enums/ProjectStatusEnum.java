package com.example.portfolio.service.enums;

import lombok.Getter;

@Getter
public enum ProjectStatusEnum {
    ON_HOLD("ON_HOLD"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String value;

    ProjectStatusEnum(String value) {
        this.value = value;
    }
}
