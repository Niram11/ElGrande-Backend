package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class RestaurantMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Field cannot be empty")
    @Size(min = 3, message = "Field must have at least 3 characters")
    @Pattern(regexp = "^[a-zA-Z]+( [a-zA-Z]+)*$",
            message = "Field must contain only letters and not start with number or whitespace")
    private String dishName;


    @NotNull
    @Positive(message = "Price must be a positive number")
    private BigDecimal price;

    @ManyToMany
    @JoinTable(name = "restaurant_menu_ingredient",
            joinColumns = @JoinColumn(name = "restaurantMenuId"),
            inverseJoinColumns = @JoinColumn(name = "ingredientId"))
    private Set<Ingredient> ingredients = new HashSet<>();

//    @OneToMany
//    private List<Restaurant> restaurants;


    public RestaurantMenu() {
    }

    public RestaurantMenu(String dishName, BigDecimal price) {
        this.dishName = dishName;
        this.price = price;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "RestaurantMenu{" +
                "id=" + id +
                ", dishName='" + dishName + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", price=" + price +
                '}';
    }

    public void assignIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }
}
