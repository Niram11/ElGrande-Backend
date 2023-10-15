package com.codecool.gastro.service;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.DetailedCustomerDto;
import com.codecool.gastro.dto.customer.EditCustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.entity.CustomerRole;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.entity.Role;
import com.codecool.gastro.service.exception.EmailNotFoundException;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.CustomerMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CustomerService  {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder encoder;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper,
                           RestaurantRepository restaurantRepository, PasswordEncoder encoder) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.restaurantRepository = restaurantRepository;
        this.encoder = encoder;
    }

    public DetailedCustomerDto getDetailedCustomerById(UUID id) {
        return customerRepository.findDetailedById(id)
                .map(customerMapper::toDetailedDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, Customer.class));
    }

    public CustomerDto saveCustomer(NewCustomerDto newCustomerDto) {
        Customer customerToSave = customerMapper.dtoToCustomer(newCustomerDto);
        assignRole(customerToSave);
        setCreationTime(customerToSave);
        encodePassword(customerToSave);
        return customerMapper.toDto(customerRepository.save(customerToSave));
    }



    public CustomerDto updateCustomer(UUID id, EditCustomerDto editCustomerDto) {
        Customer updatedCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Customer.class));

        customerMapper.updateCustomerFromDto(editCustomerDto, updatedCustomer);
        return customerMapper.toDto(customerRepository.save(updatedCustomer));
    }

    public void softDelete(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Customer.class));

        obfuscateData(customer);
        customerRepository.save(customer);
    }

    public CustomerDto getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(customerMapper::toDto)
                .orElseThrow(() -> new EmailNotFoundException(email));
    }

    public void assignRestaurantToCustomer(UUID id, UUID restaurantId) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Customer.class));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ObjectNotFoundException(restaurantId, Restaurant.class));

        customer.assignRestaurant(restaurant);
        customerRepository.save(customer);
    }

    private void obfuscateData(Customer customer) {

        // Mask surname by filling with *
        int surnameLength = customer.getSurname().length();
        customer.setSurname("*".repeat(surnameLength));

        // Mask email by filling first part of the email (before @) with *
        int atIndex = customer.getEmail().indexOf('@');
        String maskedEmail = UUID.randomUUID() + customer.getEmail().substring(atIndex);
        customer.setEmail(maskedEmail);

        customer.setDeleted(true);
    }

    private void encodePassword(Customer customerToSave) {
        customerToSave.setPassword(encoder.encode(customerToSave.getPassword()));
    }

    private void setCreationTime(Customer customerToSave) {
        customerToSave.setSubmissionTime(LocalDate.now());
    }

    private void assignRole(Customer customerToSave) {
        Role role = new Role();
        role.setRole(CustomerRole.ROLE_USER);
        customerToSave.assignRole(role);
    }

}
