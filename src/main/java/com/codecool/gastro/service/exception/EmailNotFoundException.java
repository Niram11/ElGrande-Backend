package com.codecool.gastro.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String email) {
        super(String.format("Email %s cannot be found", email));
    }

}
