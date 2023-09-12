package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class RestaurantCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank(message = "Category cannot be empty")
    private String category;
    @ManyToMany
    @JoinTable(name = "restaurant_restaurant_category", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "restaurant_category_id"))
    private final Set<Restaurant> restaurants = new HashSet<>();

    public RestaurantCategory() {
    }

    public RestaurantCategory(UUID id, String category) {
        this.id = id;
        this.category = category;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }
}