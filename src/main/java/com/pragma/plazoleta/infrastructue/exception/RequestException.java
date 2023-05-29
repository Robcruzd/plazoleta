package com.pragma.plazoleta.infrastructue.exception;

import org.springframework.http.HttpStatus;

public class RequestException extends RuntimeException {

    private HttpStatus status;

    public RequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
