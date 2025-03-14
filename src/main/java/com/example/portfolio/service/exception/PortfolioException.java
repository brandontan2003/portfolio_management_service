package com.example.portfolio.service.exception;

import lombok.Getter;

@Getter
public class PortfolioException extends RuntimeException {

    private final PortfolioErrorMessage errorMessage;

    public PortfolioException(PortfolioErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
        this.errorMessage = errorMessage;
    }
}
