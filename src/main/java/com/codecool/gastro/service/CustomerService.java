package com.codecool.gastro.service;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.DetailedCustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.dto.restaurant.RestaurantDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }


    public List<CustomerDto> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    public CustomerDto getCustomerBy(UUID id) {
        return customerRepository.findById(id)
                .map(customerMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, Customer.class));
    }

    public DetailedCustomerDto getDetailedCustomerBy(UUID id) {
        return customerRepository.findDetailedById(id)
                .map(customerMapper::toDetailedDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, Customer.class));
    }

    public CustomerDto saveCustomer(NewCustomerDto newCustomerDto) {
        Customer customerToSave = customerMapper.dtoToCustomer(newCustomerDto);
        customerToSave.setSubmissionTime(LocalDate.now());
        return customerMapper.toDto(customerRepository.save(customerToSave));
    }

    public CustomerDto updateCustomer(UUID id, NewCustomerDto newCustomerDto) {
        customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Customer.class));
        Customer updatedCustomer = customerRepository.save(customerMapper.dtoToCustomer(id, newCustomerDto));
        return customerMapper.toDto(updatedCustomer);
    }


    public void softDelete(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Customer.class));

        obfuscateData(customer);
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

}
