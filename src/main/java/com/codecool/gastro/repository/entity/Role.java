package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private CustomerRole role;
    @ManyToMany(mappedBy = "roles")
    private final Set<Customer> customers = new HashSet<>();

    public Role() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CustomerRole getRole() {
        return role;
    }

    public void setRole(CustomerRole role) {
        this.role = role;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void assignToCustomer(Customer customer) {
        customers.add(customer);
    }
}
