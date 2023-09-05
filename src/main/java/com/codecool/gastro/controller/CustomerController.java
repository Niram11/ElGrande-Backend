package com.codecool.gastro.controller;

import com.codecool.gastro.dto.customers.CustomerDto;
import com.codecool.gastro.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * This class represents the REST controller for managing customers.
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Constructor for CustomerController.
     *
     * @param customerService The service for managing customers.
     */
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Get a list of all customers.
     *
     * @return A list of CustomerDto objects representing all customers.
     */
    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getCustomers();
    }

    /**
     * Get a specific customer by their ID.
     *
     * @param id The unique identifier of the customer.
     * @return ResponseEntity containing the CustomerDto of the specified customer.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable UUID id) {
        CustomerDto customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    /**
     * Update a customer's information.
     *
     * @param id        The unique identifier of the customer to be updated.
     * @param updateDto The updated CustomerDto containing the new information.
     * @return ResponseEntity containing the updated CustomerDto.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable UUID id,
                                                      @Valid @RequestBody CustomerDto updateDto) {
        CustomerDto updatedCustomer = customerService.updateCustomer(id, updateDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    /**
     * Delete a customer by their ID.
     *
     * @param id The unique identifier of the customer to be deleted.
     * @return ResponseEntity indicating a successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
