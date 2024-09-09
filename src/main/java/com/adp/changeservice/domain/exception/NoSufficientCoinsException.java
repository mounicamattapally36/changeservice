package com.adp.changeservice.domain.exception;

public class NoSufficientCoinsException extends RuntimeException {
    public NoSufficientCoinsException(String message) {
        super(message);
    }
}
