package com.codecool.gastro.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Entity
public class RestaurantCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID restaurantId;
    @NotBlank(message = "category cannot be empty")
    private String category;

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public String getCategory() {
        return category;
    }

    public void setRestaurantId(UUID id) {
        this.restaurantId = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}