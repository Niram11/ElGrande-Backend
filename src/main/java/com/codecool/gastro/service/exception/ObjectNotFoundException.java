package com.codecool.gastro.service.exception;

import java.util.UUID;

public class ObjectNotFoundException extends RuntimeException{
    private final UUID id;
    private final Class<?> aClass;
    public ObjectNotFoundException(UUID id, Class<?> aClass) {
        super(String.format("Object of class %s and id %s could not be found", aClass.getSimpleName(), id));
        this.id = id;
        this.aClass = aClass;
    }

    public UUID getId() {
        return id;
    }

    public Class<?> getaClass() {
        return aClass;
    }
}
