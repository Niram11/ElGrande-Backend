package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class MenuCategory {
    // TODO: Change name to "DishCategory"
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "menu category cannot be empty")
    private String category;

    @ManyToMany(mappedBy = "categories")
    private final Set<RestaurantMenu> restaurantMenus = new HashSet<>();

    public MenuCategory(UUID id, String category) {
        this.id = id;
        this.category = category;
    }

    public MenuCategory() {
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

    public Set<RestaurantMenu> getRestaurantMenu() {
        return restaurantMenus;
    }
}
