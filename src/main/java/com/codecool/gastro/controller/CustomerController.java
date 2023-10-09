package com.codecool.gastro.controller;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.DetailedCustomerDto;
import com.codecool.gastro.dto.customer.EditCustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<DetailedCustomerDto> getDetailedCustomerById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getDetailedCustomerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable UUID id, @Valid @RequestBody EditCustomerDto updateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.updateCustomer(id, updateDto));
    }

    @PutMapping("/{id}/restaurants/{restaurantId}")
    public ResponseEntity<Void> assignRestaurantToCustomer(@PathVariable UUID id, @PathVariable UUID restaurantId) {
        customerService.assignRestaurantToCustomer(id, restaurantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        customerService.softDelete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
