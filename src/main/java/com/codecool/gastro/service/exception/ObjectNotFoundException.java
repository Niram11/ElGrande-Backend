package com.codecool.gastro.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(UUID id, Class<?> aClass) {
        super(String.format("Object of class %s and id %s cannot be found", aClass.getSimpleName(), id));
    }
}
