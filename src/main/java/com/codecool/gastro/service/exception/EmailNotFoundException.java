package com.codecool.gastro.service.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String email) {
        super(String.format("Email %s cannot be found", email));
    }
}