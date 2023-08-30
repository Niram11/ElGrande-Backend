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
    List<Customer> findAllBy();
    @Query("SELECT c FROM Customer c WHERE c.id = :id")
    Optional<Customer> findBy(UUID id);
}