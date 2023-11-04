package com.codecool.gastro.controller.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TimeFormatValidatorTest {

    private TimeFormatValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new TimeFormatValidator();
    }

    @Test
    void testTimeFormatValidatorWithValidData() {
        LocalTime validTime = LocalTime.of(12, 30);
        assertTrue(validator.isValid(validTime, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testTimeFormatValidatorWithInvalidData() {
        LocalTime invalidTime = LocalTime.of(23, 39, 13);
        assertFalse(validator.isValid(invalidTime, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testTimeFormatValidatorWithNullValue() {
        assertFalse(validator.isValid(null, mock(ConstraintValidatorContext.class)));
    }
}
