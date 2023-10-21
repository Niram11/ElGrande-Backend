package com.codecool.gastro.security.jwt.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TokenRefreshException extends RuntimeException {
    public TokenRefreshException(String token) {
        super(String.format("Failed for [%s]: Refresh token has expired. Please make a new login request", token));
    }
}
