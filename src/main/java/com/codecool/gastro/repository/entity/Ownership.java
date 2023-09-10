package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Ownership {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    @OneToMany
    private final Set<Restaurant> restaurants = new HashSet<>();

    public Ownership() {
    }

    public Ownership(UUID id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }
}
