package com.codecool.gastro.service;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.service.mapper.CustomerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService
{
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper)
    {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDto saveCustomer(NewCustomerDto newCustomerDto)
    {
        Customer savedCustomer = customerRepository.save(customerMapper.dtoToCustomer(newCustomerDto));
        return customerMapper.customerToDto(savedCustomer);
    }

    public List<CustomerDto> getCustomers()
    {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToDto)
                .toList();
    }

    public CustomerDto getCustomerById(UUID id)
    {
        return customerRepository.findById(id)
                .map(customerMapper::customerToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public CustomerDto updateCustomer(UUID id, NewCustomerDto updateDto) {
        getCustomerEntityById(id);
        Customer updatedCustomer = customerMapper.dtoToCustomer(updateDto, id);

        updatedCustomer.setSurname(updateDto.surname());
        updatedCustomer.setForename(updateDto.forename());
        updatedCustomer.setEmail(updateDto.email());

        customerRepository.save(updatedCustomer);
        return customerMapper.customerToDto(updatedCustomer);
    }

    private Customer getCustomerEntityById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteCustomer(UUID id) {
        Customer existingCustomer = getCustomerEntityById(id);

        // Mask surname by filling with *
        String surname = existingCustomer.getSurname();
        String maskedSurname = "*".repeat(surname.length());
        existingCustomer.setSurname(maskedSurname);

        // Mask email by filling first part of the email (before @) with *
        UUID emailId = UUID.randomUUID();
        String email = existingCustomer.getEmail();
        int atIndex = email.indexOf('@');
        String maskedEmail = emailId + "*".repeat(atIndex) + email.substring(atIndex);
        existingCustomer.setEmail(maskedEmail);

        customerRepository.save(existingCustomer);
    }
}
