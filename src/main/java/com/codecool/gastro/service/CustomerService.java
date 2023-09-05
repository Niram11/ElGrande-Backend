package com.codecool.gastro.service;

import com.codecool.gastro.dto.customers.CustomerDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.service.mapper.CustomerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
                .map(customerMapper::customerToDto)
                .toList();
    }

    public CustomerDto getCustomerById(UUID id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public CustomerDto updateForename(UUID id, String newForename) {
        Customer existingCustomer = getCustomerEntityById(id);
        existingCustomer.setForename(newForename);

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return customerMapper.customerToDto(updatedCustomer);
    }

    public CustomerDto updateSurname(UUID id, String newSurname) {
        Customer existingCustomer = getCustomerEntityById(id);
        existingCustomer.setSurname(newSurname);

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return customerMapper.customerToDto(updatedCustomer);
    }

    public CustomerDto updateEmail(UUID id, String newEmail) {
        Customer existingCustomer = getCustomerEntityById(id);
        existingCustomer.setEmail(newEmail);

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return customerMapper.customerToDto(updatedCustomer);
    }

    private Customer getCustomerEntityById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public CustomerDto updateCustomer(UUID id, CustomerDto updateDto) {
        getCustomerEntityById(id);
        Customer updatedCustomer = customerMapper.dtoToCustomer(updateDto, id);

        customerRepository.save(updatedCustomer);
        return customerMapper.customerToDto(updatedCustomer);
    }

    public void deleteCustomer(UUID id) {
        Customer existingCustomer = getCustomerEntityById(id);

        String surname = existingCustomer.getSurname();
        String maskedSurname = "*".repeat(surname.length());
        existingCustomer.setSurname(maskedSurname);

        UUID emailId = UUID.randomUUID();
        String email = existingCustomer.getEmail();
        int atIndex = email.indexOf('@');
        String maskedEmail = emailId + "*".repeat(atIndex) + email.substring(atIndex);
        existingCustomer.setEmail(maskedEmail);

        customerRepository.save(existingCustomer);
    }
}
