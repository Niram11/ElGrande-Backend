package com.codecool.gastro.service.validation;


public interface Validation<T>{

    void validateUpdate(T dto);
}