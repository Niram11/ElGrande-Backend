package com.codecool.gastro.service.form.handler;

import com.codecool.gastro.dto.form.NewRestaurantFormDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomerHandler implements FormHandler<Customer> {
    private final CustomerRepository customerRepository;

    public CustomerHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void handleRestaurantForm(NewRestaurantFormDto formDto, Restaurant restaurant) {
        customerRepository.findById(formDto.customerId())
                .ifPresentOrElse(customer -> {
                            customer.assignRestaurant(restaurant);
                            customerRepository.save(customer);
                        }, () -> {
                            throw new ObjectNotFoundException(formDto.customerId(), Customer.class);
                        });
    }
}
