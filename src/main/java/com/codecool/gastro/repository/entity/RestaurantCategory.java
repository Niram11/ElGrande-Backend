package com.codecool.gastro.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class RestaurantCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID restaurantId;
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
