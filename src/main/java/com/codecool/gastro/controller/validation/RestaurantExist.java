package com.codecool.gastro.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RestaurantExistValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestaurantExist {
    String message() default "Restaurant with this id does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
