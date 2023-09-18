package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Dish {
    public static final String REGEX_FOR_DISH = "^[a-zA-Z][a-zA-Z' ]*$";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Dish name cannot be empty")
    @Pattern(regexp = REGEX_FOR_DISH,
            message = "Dish must contain only letters and not start with number or whitespace")
    private String dishName;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive number")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @ManyToMany
    @JoinTable(name = "dish_ingredients",
            joinColumns = @JoinColumn(name = "dishId"),
            inverseJoinColumns = @JoinColumn(name = "ingredientId"))
    private final Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "dish_dish_category",
            joinColumns = @JoinColumn(name = "dishId"),
            inverseJoinColumns = @JoinColumn(name = "dishCategoryId"))
    private final Set<DishCategory> categories = new HashSet<>();

    public Dish() {
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

    public Set<DishCategory> getCategories() {
        return categories;
    }

    public void assignIngredient(Ingredient addedIngredients) {
        ingredients.add(addedIngredients);
    }

    public void assignCategories(DishCategory addedIngredients) {
        categories.add(addedIngredients);
    }

}
