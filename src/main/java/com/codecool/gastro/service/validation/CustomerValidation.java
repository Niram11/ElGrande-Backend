package com.codecool.gastro.service.validation;

import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.EmailNotFoundException;
import com.codecool.gastro.service.exception.ObjectNotFoundException;

import java.util.UUID;

public class CustomerValidation implements Validation<UUID>{
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;

    public CustomerValidation(CustomerRepository customerRepository, RestaurantRepository restaurantRepository) {
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void validateUpdate(UUID id) {
        customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Customer.class));
    }

    public void validateGetDetailedCustomerById(UUID id) {
        customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Customer.class));
    }

    public void validateSoftDelete(UUID id) {
        customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Customer.class));
    }

    public void validateGetCustomerByEmail(String email) {
        customerRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));
    }

    public void validateAssignRestaurantToCustomer(UUID customerId, UUID restaurantId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException(customerId, Customer.class));
        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ObjectNotFoundException(customerId, Restaurant.class));
    }
}
