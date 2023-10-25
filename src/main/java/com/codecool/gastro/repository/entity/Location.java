package com.codecool.gastro.repository.entity;

import com.codecool.gastro.dto.restaurant.RestaurantDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Location implements EntityObject {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull(message = "Latitude cannot be null")
    @Column(precision = 32, scale = 10)
    private BigDecimal latitude;
    @NotNull(message = "Longitude cannot be null")
    @Column(precision = 32, scale = 10)
    private BigDecimal longitude;
    @OneToMany
    private final Set<Restaurant> restaurants = new HashSet<>();

    public Location() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void assignRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }
}
