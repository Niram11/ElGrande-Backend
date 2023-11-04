package com.codecool.gastro.security.jwt.service.exception;

import java.util.UUID;

public class TokenAlreadyExistException extends RuntimeException{
    public TokenAlreadyExistException(UUID customerId) {
        super(String.format("Failed for customer [%s]: Refresh accessToken already exist, " +
                "if you lost it please make a logout request and try again", customerId.toString()));
    }
}
