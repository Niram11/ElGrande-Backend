package com.codecool.gastro.service.validation;


public interface Validation<T>{

    void validateEntityById(T dto);
    //TODO: change names
}