package com.example.rdas.domain.exception;

import org.springframework.http.HttpStatus;

public class ReferenceDataException extends RuntimeException {
    private final String code;
    private final HttpStatus status;

    public ReferenceDataException(String code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public String code() {
        return code;
    }

    public HttpStatus status() {
        return status;
    }
}
