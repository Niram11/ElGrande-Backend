package com.codecool.gastro.controller;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping
    public CustomerDto createNewCustomer(@Valid @RequestBody NewCustomerDto newCustomerDto) {
        return customerService.saveCustomer(newCustomerDto);
    }

    @GetMapping("/{id}")
    public CustomerDto getAllCustomers(@PathVariable UUID id) {
        return customerService.getCustomerBy(id);
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable UUID id,
                                      @Valid @RequestBody NewCustomerDto updateDto) {
        return customerService.updateCustomer(id, updateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable UUID id) {
        customerService.softDelete(id);
    }
}
