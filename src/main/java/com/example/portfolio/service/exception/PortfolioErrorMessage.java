package com.example.portfolio.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.example.portfolio.service.constant.ErrorConstant.HOMEPAGE_SECTION_NOT_FOUND_ERROR_CODE;
import static com.example.portfolio.service.constant.ErrorConstant.HOMEPAGE_SECTION_NOT_FOUND_ERROR_DESC;

@Getter
public enum PortfolioErrorMessage {

    HOMEPAGE_SECTION_NOT_FOUND(HttpStatus.NOT_FOUND, HOMEPAGE_SECTION_NOT_FOUND_ERROR_CODE, HOMEPAGE_SECTION_NOT_FOUND_ERROR_DESC);

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    // Constructor to initialize the enum constants
    PortfolioErrorMessage(HttpStatus httpStatus, String errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
