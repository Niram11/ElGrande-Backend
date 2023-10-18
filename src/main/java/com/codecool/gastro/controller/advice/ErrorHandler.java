package com.codecool.gastro.controller.advice;

import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.security.jwt.service.exception.TokenAlreadyExistException;
import com.codecool.gastro.security.jwt.service.exception.TokenRefreshException;
import jakarta.validation.ConstraintViolationException;
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

    @ExceptionHandler(value = {ObjectNotFoundException.class, ObjectNotFoundException.class,
            TokenAlreadyExistException.class, TokenRefreshException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotObjectFound(RuntimeException ex) {
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
        String errMsg = "One or more fields are incorrect type";
        return new ErrorResponse(errMsg);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errMsg = "Operation cannot be completed due to incorrect data";
        return new ErrorResponse(errMsg);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
        String errMsg = "One or more fields are does not meet requirements";
        return new ErrorResponse(errMsg);
    }

//    @ExceptionHandler(value = MalformedJwtException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleMalformedJwtException(MalformedJwtException ex) {
//        String errMsg = "Invalid JWT token: " + ex.getMessage();
//        return new ErrorResponse(errMsg);
//    }
//
//    @ExceptionHandler(value = UnsupportedJwtException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleUnsupportedJwtException(UnsupportedJwtException ex) {
//        String errMsg = "JWT token is unsupported: " + ex.getMessage();
//        return new ErrorResponse(errMsg);
//    }
//
//    @ExceptionHandler(value = SignatureException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleSignatureException(SignatureException ex) {
//        String errMsg = "Invalid JWT signature: " + ex.getMessage();
//        return new ErrorResponse(errMsg);
//    }
//
//    @ExceptionHandler(value = ExpiredJwtException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleExpiredJwtException(ExpiredJwtException ex) {
//        String errMsg = "JWT token is expired: " + ex.getMessage();
//        return new ErrorResponse(errMsg);
//    }

    public record ErrorResponse(String errorMessage) {

    }
}

