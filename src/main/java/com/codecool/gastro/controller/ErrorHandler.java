package com.codecool.gastro.controller;

import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.hibernate.TransientPropertyValueException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotObjectFound(ObjectNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotValidArgument(MethodArgumentNotValidException ex) {
        String errMsg = ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" | "));

        return new ErrorResponse(errMsg);
    }

    @ExceptionHandler(value = TransientPropertyValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNullPropertyInNotNullableObject(TransientPropertyValueException ex) {
        String errMsg = "Field that references " + ex.getPropertyName() + " cannot be of null value";
        return new ErrorResponse(errMsg);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotReadableException(HttpMessageNotReadableException ex) {
        String errMsg = "One or more fields are not readable";
        return new ErrorResponse(errMsg);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errMsg = "Operation cannot be completed due to incorrect data";
        return new ErrorResponse(errMsg);
    }

    public record ErrorResponse(String errorMessage) {

    }
}

