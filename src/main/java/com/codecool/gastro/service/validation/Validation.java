package com.codecool.gastro.service.validation;


public interface Validation<T, U>{

    U validateEntityById(T dto);
}