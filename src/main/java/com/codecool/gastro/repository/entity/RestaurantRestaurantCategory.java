package com.codecool.gastro.repository.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Entity
public class RestaurantRestaurantCategory {

    @Id
    @NotBlank(message = "Restaurant Id cannot be empty")
    private UUID restaurantId;
    @NotBlank(message = "Restaurant category Id cannot be empty")
    private UUID restaurantCategoryId;

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public UUID getRestaurantCategoryId() {
        return restaurantCategoryId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setRestaurantCategoryId(UUID restaurantCategoryId) {
        this.restaurantCategoryId = restaurantCategoryId;
    }
}
