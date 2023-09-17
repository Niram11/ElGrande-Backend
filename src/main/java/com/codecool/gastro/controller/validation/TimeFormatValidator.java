package com.codecool.gastro.controller.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class TimeFormatValidator implements ConstraintValidator<TimeFormat, LocalTime> {
    public TimeFormatValidator() {
    }

    @Override
    public boolean isValid(LocalTime time, ConstraintValidatorContext context) {
        if (Objects.isNull(time)) {
            return false;
        }
        try {
            LocalTime.parse(time.toString(), DateTimeFormatter.ofPattern("HH:mm"));
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
