package com.codecool.gastro.service.exception;

import com.codecool.gastro.repository.entity.Restaurant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ObjectNotFoundExceptionTest {

    @Test
    void objectNotFoundExceptionTest() {
        UUID id = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();
        try {
            throw new ObjectNotFoundException(id, Restaurant.class);
        } catch (ObjectNotFoundException e) {
            Assertions.assertEquals("Object of class " + Restaurant.class.getSimpleName() + " and id " + id + " cannot be found", e.getMessage());
        }
    }
}
