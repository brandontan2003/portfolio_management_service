package com.example.portfolio.service.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.example.portfolio.service.common.constant.CommonErrorConstant.FIELD_VALIDATION_ERROR_CODE;
import static com.example.portfolio.service.common.constant.CommonErrorConstant.FIELD_VALIDATION_ERROR_DESC;


@Getter
public enum CommonErrorMessage {

    FIELD_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, FIELD_VALIDATION_ERROR_CODE, FIELD_VALIDATION_ERROR_DESC);

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    // Constructor to initialize the enum constants
    CommonErrorMessage(HttpStatus httpStatus, String errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
