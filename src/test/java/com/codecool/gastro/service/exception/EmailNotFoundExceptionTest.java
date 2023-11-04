package com.codecool.gastro.service.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class EmailNotFoundExceptionTest {
    @Test
    void testEmailNotFoundExceptionShould() {
        String email = "example@xd.pl";
        try {
            throw new EmailNotFoundException(email);
        } catch (EmailNotFoundException e) {
            Assertions.assertEquals(e.getMessage(), "Email " + email + " cannot be found");
        }
    }
}