package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class RestaurantMenu {

    public static final String REGEX_FOR_MENU = "^[a-zA-Z][a-zA-Z' ]*$";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Field cannot be empty")
    @Size(min = 3, message = "Field must have at least 3 characters")
    @Pattern(regexp = REGEX_FOR_MENU,
            message = "Field must contain only letters and not start with number or whitespace")
    private String dishName;

    @NotNull(message = "Field must not be null")
    @Positive(message = "Price must be a positive number")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @ManyToMany
    @JoinTable(name = "restaurant_menu_ingredients",
            joinColumns = @JoinColumn(name = "restaurantMenuId"),
            inverseJoinColumns = @JoinColumn(name = "ingredientId"))
    private final Set<Ingredient> ingredients = new HashSet<>();

    public RestaurantMenu() {
    }

    public RestaurantMenu(UUID id, String dishName, BigDecimal price, Restaurant restaurant) {
        this.id = id;
        this.dishName = dishName;
        this.price = price;
        this.restaurant = restaurant;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void assignIngredient(Ingredient addedIngredients) {
        ingredients.add(addedIngredients);
    }
}
