package com.smoke.vodafonemvpapi.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;

    public ErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
