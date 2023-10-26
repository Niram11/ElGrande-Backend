package com.codecool.gastro.service;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.DetailedCustomerDto;
import com.codecool.gastro.dto.customer.EditCustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.entity.CustomerRole;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.entity.Role;
import com.codecool.gastro.security.jwt.repository.OAuth2ClientTokenRepository;
import com.codecool.gastro.security.jwt.repository.RefreshTokenRepository;
import com.codecool.gastro.service.mapper.CustomerMapper;
import com.codecool.gastro.service.validation.CustomerValidation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final RestaurantRepository restaurantRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2ClientTokenRepository oAuth2ClientTokenRepository;
    private final PasswordEncoder encoder;
    private final CustomerValidation validation;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper,
                           RestaurantRepository restaurantRepository, RefreshTokenRepository refreshTokenRepository,
                           OAuth2ClientTokenRepository oAuth2ClientTokenRepository, PasswordEncoder encoder,
                           CustomerValidation validation) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.restaurantRepository = restaurantRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.oAuth2ClientTokenRepository = oAuth2ClientTokenRepository;
        this.encoder = encoder;
        this.validation = validation;
    }

    public DetailedCustomerDto getDetailedCustomerById(UUID id) {
        validation.validateEntityById(id);
        return customerRepository.findDetailedById(id)
                .map(customerMapper::toDetailedDto).get();
    }

    public CustomerDto saveCustomer(NewCustomerDto newCustomerDto) {
        Customer customerToSave = customerMapper.dtoToCustomer(newCustomerDto);
        assignRole(customerToSave);
        setCreationTime(customerToSave);
        encodePassword(customerToSave);
        return customerMapper.toDto(customerRepository.save(customerToSave));
    }


    public CustomerDto updateCustomer(UUID id, EditCustomerDto editCustomerDto) {
        Customer updatedCustomer = validation.validateEntityById(id);
        customerMapper.updateCustomerFromDto(editCustomerDto, updatedCustomer);
        return customerMapper.toDto(customerRepository.save(updatedCustomer));
    }

    public void softDelete(UUID id) {
        Customer customer = validation.validateEntityById(id);
        refreshTokenRepository.findByCustomerId(id).ifPresent(refreshTokenRepository::delete);
        oAuth2ClientTokenRepository.findByCustomerId(id).ifPresent(oAuth2ClientTokenRepository::delete);
        obfuscateData(customer);
        customerRepository.save(customer);
    }

    public CustomerDto getCustomerByEmail(String email) {
        validation.validateGetCustomerByEmail(email);
        return customerRepository.findByEmail(email)
                .map(customerMapper::toDto).get();
    }

    public void assignRestaurantToCustomer(UUID id, UUID restaurantId) {
        validation.validateAssignRestaurantToCustomer(id, restaurantId);
        Customer customer = customerRepository.findById(id).get();
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        customer.assignRestaurant(restaurant);
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

    private void encodePassword(Customer customerToSave) {
        customerToSave.setPassword(encoder.encode(customerToSave.getPassword()));
    }

    private void setCreationTime(Customer customerToSave) {
        customerToSave.setSubmissionTime(LocalDate.now());
    }

    private void assignRole(Customer customerToSave) {
        Role role = new Role();
        role.setRole(CustomerRole.ROLE_USER);
        customerToSave.assignRole(role);
    }
}
