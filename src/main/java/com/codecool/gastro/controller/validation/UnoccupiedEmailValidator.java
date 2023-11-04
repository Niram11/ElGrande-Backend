package com.codecool.gastro.controller.validation;

import com.codecool.gastro.repository.CustomerRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UnoccupiedEmailValidator implements ConstraintValidator<UnoccupiedEmail, String> {

    private final CustomerRepository customerRepository;

    public UnoccupiedEmailValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return customerRepository.findByEmail(email).isEmpty();
    }
}
