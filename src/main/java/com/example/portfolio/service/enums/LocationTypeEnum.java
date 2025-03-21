package com.example.portfolio.service.enums;

import lombok.Getter;

@Getter
public enum LocationTypeEnum {
    REMOTE("REMOTE"),
    HYBRID("HYBRID"),
    ON_SITE("ON_SITE");

    private final String value;

    LocationTypeEnum(String value) {
        this.value = value;
    }
}
