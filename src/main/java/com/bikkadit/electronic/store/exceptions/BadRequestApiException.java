package com.bikkadit.electronic.store.exceptions;

public class BadRequestApiException extends RuntimeException {

    public BadRequestApiException() {
        super(" Bad Request ....");
    }

    public BadRequestApiException(String msg) {
        super(msg);
    }
}
