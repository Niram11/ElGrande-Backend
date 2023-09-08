package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID>
{
    @Query("SELECT customer from Customer customer")
    List<Customer> findAllBy();

    @Query("SELECT customer FROM Customer customer WHERE customer.id = :id")
    Optional<Customer> findBy(UUID id);
}
