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
    void testFindAll_ShouldReturnListOfCustomer_WhenCalled() {
        // then
        List<Customer> list = repository.findAll();

        // test
        assertEquals(2, list.size());
    }

    @Test
    void testFindById_ShouldReturnCustomer_WhenExist() {
        // then
        Optional<Customer> customer = repository.findById(customerId);

        // test
        assertTrue(customer.isPresent());
    }

    @Test
    void testFindById_ShouldReturnEmptyOptional_WhenNoCustomer() {
        // then
        Optional<Customer> customer = repository.findById(UUID.randomUUID());

        // test
        assertTrue(customer.isEmpty());
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

    @Test
    void testSave_ShouldReturnNewCustomer_WhenCalledWithoutId() {
        // given
        Customer customer = new Customer();
        customer.setName("Name");
        customer.setSurname("Surname");
        customer.setEmail("Email@wp.pl");
        customer.setSubmissionTime(LocalDate.of(2010, 10, 10));
        customer.setPasswordHash("PW");

        // then
        Customer savedCustomer = repository.save(customer);

        // test
        assertEquals(savedCustomer, customer);
    }

    @Test
    void testSave_ShouldReturnUpdatedCustomer_WhenCalledWithExistingId() {
        // given
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Name");
        customer.setSurname("Surname");
        customer.setEmail("Email@wp.pl");
        customer.setSubmissionTime(LocalDate.of(2010, 10, 10));
        customer.setPasswordHash("PW");

        // then
        Customer savedCustomer = repository.save(customer);
        Optional<Customer> customerById = repository.findById(customerId);

        // test
        assertEquals(customerById.get(), savedCustomer);
    }

    @Test
    void testDelete_ShouldReturnEmptyOptional_WhenFindById() {
        // given
        Customer customer = new Customer();
        customer.setId(customerId);

        // then
        repository.delete(customer);
        Optional<Customer> deletedCustomer = repository.findById(customerId);

        // test
        assertTrue(deletedCustomer.isEmpty());
    }

}
