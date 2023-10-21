package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.projection.DetailedCustomerProjection;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CustomerRepositoryTest {
    @Autowired
    CustomerRepository repository;

    private UUID customerId;
    private String customerEmail;

    @BeforeEach
    void setUp() {
        customerId = UUID.fromString("89b77b49-562f-4570-ae40-52b4e359cb5f");
        customerEmail = "Oskar@wp.pl";
    }

    @Test
    void testFindByEmail_ShouldReturnCustomer_WhenExist() {
        // then
        Optional<Customer> customer = repository.findByEmail(customerEmail);

        // test
        assertTrue(customer.isPresent());
    }

    @Test
    void testFindByEmail_ShouldReturnEmptyOptional_WhenNoCustomer() {
        // then
        Optional<Customer> customer = repository.findByEmail("Email@wp.pl");

        // test
        assertTrue(customer.isEmpty());
    }

    @Test
    void testFindDetailedById_ShouldReturnDetailerCustomerProjection_WhenExist() {
        // then
        Optional<DetailedCustomerProjection> projection = repository.findDetailedById(customerId);

        // test
        assertTrue(projection.isPresent());
    }

    @Test
    void testFindDetailedById_ShouldReturnEmptyOptional_WhenNoCustomer() {
        // then
        Optional<DetailedCustomerProjection> projection = repository.findDetailedById(UUID.randomUUID());

        // test
        assertTrue(projection.isEmpty());
    }


}
