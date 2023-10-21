package com.codecool.gastro.security.jwt.service.exception;

public class SessionNotRegisteredException extends RuntimeException {
    public SessionNotRegisteredException(String jSessionId) {
        super(String.format("Session %s was not registered, please make another login request", jSessionId));
    }
}
