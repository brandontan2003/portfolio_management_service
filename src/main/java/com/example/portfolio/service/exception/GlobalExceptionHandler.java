package com.example.portfolio.service.exception;

import com.example.portfolio.service.common.dto.ResponsePayload;
import com.example.portfolio.service.common.dto.error.Error;
import com.example.portfolio.service.common.dto.error.ErrorPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.portfolio.service.common.constant.ApiConstant.STATUS_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PortfolioException.class)
    public ResponseEntity<ResponsePayload<ErrorPayload>> handleProductException(PortfolioException ex) {
        PortfolioErrorMessage err = ex.getErrorMessage();
        return ResponseEntity.status(err.getHttpStatus()).body(ResponsePayload.<ErrorPayload>builder()
                .status(STATUS_ERROR).result(ErrorPayload.builder().error(getError(err)).build()).build());
    }

    private static Error getError(PortfolioErrorMessage err) {
        return Error.builder().errorCode(err.getErrorCode()).errorMessage(err.getErrorMessage()).build();
    }

}
