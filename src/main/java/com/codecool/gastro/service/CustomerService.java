package com.codecool.gastro.service;

import com.codecool.gastro.dto.customers.CustomerDto;
import com.codecool.gastro.repository.CustomerRepository;
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

    CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper)
    {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDto> getCustomers()
    {
        return customerRepository.findAllBy()
                .stream()
                .map(customerMapper::customerToDto).toList();
    }

    public CustomerDto getCustomerById(UUID id)
    {
        return customerRepository.findById(id)
                .map(customerMapper::customerToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
