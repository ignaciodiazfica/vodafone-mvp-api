package com.smoke.vodafonemvpapi.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Custom exception
    @ExceptionHandler(TemperatureNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTemperatureNotFound(TemperatureNotFoundException ex){
        return new ErrorResponse("TEMPERATURE_NOT_FOUND", ex.getMessage());
    }

    // Bean validation exception
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex){
        return new ErrorResponse("BAD_REQUEST", ex.getMessage());
    }

    // Generic exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex){
        return new ErrorResponse("INTERNAL_SERVER_ERROR", String.format("An unexpected error occurred: %s", ex.getMessage()));
    }

}
