package com.codecool.gastro.service.validation;


import java.util.UUID;

public interface Validation<T>{
    T validateEntityById(UUID id);
}