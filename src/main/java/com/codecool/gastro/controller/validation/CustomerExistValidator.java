package com.codecool.gastro.controller.validation;

import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerExistValidator implements ConstraintValidator<CustomerExist, UUID> {

    private final CustomerRepository customerRepository;

    public CustomerExistValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext context) {
        return customerRepository.findById(id).isPresent();
    }
}
