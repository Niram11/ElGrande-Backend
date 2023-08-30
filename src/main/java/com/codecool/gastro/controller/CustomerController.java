package com.codecool.gastro.controller;

import com.codecool.gastro.dto.customers.CustomerDto;
import com.codecool.gastro.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController
{
    private CustomerService customerService;

    @GetMapping
    public List<CustomerDto> getAllCustomers()
    {
        return customerService.getCustomers();
    }

    @GetMapping("/{uuid}")
    public CustomerDto getCustomer(@PathVariable UUID id)
    {
        return customerService.getCustomerById(id);
    }
}
