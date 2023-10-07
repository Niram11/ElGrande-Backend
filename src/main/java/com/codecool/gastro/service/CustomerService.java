package com.codecool.gastro.service;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.DetailedCustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.service.exception.EmailNotFoundException;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.CustomerMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder encoder;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, PasswordEncoder encoder) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.encoder = encoder;
    }


    public CustomerDto getCustomerById(UUID id) {
        return customerRepository.findById(id)
                .map(customerMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, Customer.class));
    }

    public DetailedCustomerDto getDetailedCustomerById(UUID id) {
        return customerRepository.findDetailedById(id)
                .map(customerMapper::toDetailedDto)
                .orElseThrow(() -> new ObjectNotFoundException(id, Customer.class));
    }

    public CustomerDto saveCustomer(NewCustomerDto newCustomerDto) {
        Customer customerToSave = customerMapper.dtoToCustomer(newCustomerDto);
        customerToSave.setSubmissionTime(LocalDate.now());
        customerToSave.setPassword(encoder.encode(customerToSave.getPassword()));
        return customerMapper.toDto(customerRepository.save(customerToSave));
    }

    public CustomerDto updateCustomer(UUID id, NewCustomerDto newCustomerDto) {
        customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Customer.class));
        Customer customerToUpdate = customerMapper.dtoToCustomer(id, newCustomerDto);
        customerToUpdate.setPassword(encoder.encode(customerToUpdate.getPassword()));
        return customerMapper.toDto(customerRepository.save(customerToUpdate));
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

    public CustomerDto getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(customerMapper::toDto)
                .orElseThrow(() -> new EmailNotFoundException(email));
    }
}
