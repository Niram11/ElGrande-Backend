package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class RestaurantMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "cannot be empty")
    private String dishName;


    @NotNull
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
}
