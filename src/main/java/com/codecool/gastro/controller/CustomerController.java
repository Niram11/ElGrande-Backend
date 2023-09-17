package com.codecool.gastro.controller;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.DetailedCustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(customerService.getCustomerBy(id));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<DetailedCustomerDto> getDetailedCustomer(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(customerService.getDetailedCustomerBy(id));
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createNewCustomer(@Valid @RequestBody NewCustomerDto newCustomerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.saveCustomer(newCustomerDto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable UUID id, @Valid @RequestBody NewCustomerDto updateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.updateCustomer(id, updateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDto> deleteCustomer(@PathVariable UUID id) {
        customerService.softDelete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
