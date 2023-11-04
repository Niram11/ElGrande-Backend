package com.codecool.gastro.controller.validation;

import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UnoccupiedEmailEmailValidatorTest {

    private UnoccupiedEmailValidator unoccupiedEmailValidator;
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        unoccupiedEmailValidator = new UnoccupiedEmailValidator(customerRepository);
    }

    @Test
    void testUnoccupiedValidatorWithRightDataShouldReturnTrue() {
        //given
        String email = "test@example.com";
        //act
        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());
        //assert
        assertTrue(unoccupiedEmailValidator.isValid(email, null));
    }

    @Test
    void testUnoccupiedValidatorWithWrongDataShouldReturnFalse() {
        //given
        String email = "test@example.com";
        //act
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(new Customer()));
        //assert
        assertFalse(unoccupiedEmailValidator.isValid(email, null));
    }
}
