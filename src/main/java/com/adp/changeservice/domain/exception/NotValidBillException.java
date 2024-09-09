package com.adp.changeservice.domain.exception;

public class NotValidBillException extends RuntimeException{
    public NotValidBillException(String message) {
        super(message);
    }
}
