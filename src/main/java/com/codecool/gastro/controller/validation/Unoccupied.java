package com.codecool.gastro.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UnoccupiedValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unoccupied {
    String message() default "This email is already used";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
