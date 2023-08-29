package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class RestaurantMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "cannot be empty")
    private String dishName;

    @NotBlank(message = "cannot be empty")
    private String ingredients;

    @NotNull
    private BigDecimal price;

//    @OneToMany
//    private List<Restaurant> restaurants;


    public RestaurantMenu() {
    }

    public RestaurantMenu(String dishName, String ingredients, BigDecimal price) {
        this.dishName = dishName;
        this.ingredients = ingredients;
        this.price = price;
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
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
}
