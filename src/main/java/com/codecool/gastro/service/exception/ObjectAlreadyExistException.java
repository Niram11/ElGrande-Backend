package com.codecool.gastro.service.exception;

import java.util.UUID;

public class ObjectAlreadyExistException extends RuntimeException{
    public ObjectAlreadyExistException(UUID id, Class<?> aClass) {
        super(String.format("Object of class %s and id %s already exist", aClass.getSimpleName(), id));
    }
}
