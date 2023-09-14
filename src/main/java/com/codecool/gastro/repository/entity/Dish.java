package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Dish {
    // TODO: Change name to "Dish"
    public static final String REGEX_FOR_DISH = "^[a-zA-Z][a-zA-Z' ]*$";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Field cannot be empty")
    @Size(min = 3, message = "Field must have at least 3 characters")
    @Pattern(regexp = REGEX_FOR_DISH,
            message = "Field must contain only letters and not start with number or whitespace")
    private String dishName;

    @NotNull(message = "Field must not be null")
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

    public Dish(UUID id, String dishName, BigDecimal price, Restaurant restaurant) {
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
