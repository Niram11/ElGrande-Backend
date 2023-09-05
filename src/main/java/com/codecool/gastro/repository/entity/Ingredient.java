package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Ingredient {

    public static final String REGEX_FOR_INGREDIENT = "^[a-zA-Z][a-zA-Z ]*$";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Field cannot be empty")
    @Size(min = 3, message = "Field must have at least 3 characters")
    @Pattern(regexp = REGEX_FOR_INGREDIENT,
            message = "Field must contain only letters and not start with number or whitespace")
    private String name;
// TODO tylko z małej litery
    @ManyToMany(mappedBy = "ingredients")
    private Set<RestaurantMenu> restaurantMenus = new HashSet<>();

    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RestaurantMenu> getRestaurantMenus() {
        return restaurantMenus;
    }

    public void setRestaurantMenus(Set<RestaurantMenu> restaurantMenus) {
        this.restaurantMenus = restaurantMenus;
    }

    public void addRestaurantMenu(RestaurantMenu menu) {
        restaurantMenus.add(menu);
    }
}
