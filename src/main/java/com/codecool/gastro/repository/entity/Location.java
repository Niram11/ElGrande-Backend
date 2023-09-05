package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank(message = "Latitude cannot be empty")
    private double latitude;
    @NotBlank(message = "Longitude Id cannot be empty")
    private double longitude;
//    @OneToMany
//    @JoinColumn(name = "restaurant_id", nullable = false)
//    private Restaurant restaurantId;

    public Location() {
    }

    //    add Restaurant restaurant_UUID
    public Location(UUID id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
//    this.restaurantId = restaurantId;
    }

    public UUID getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

//    public Restaurant getRestaurantUUID() {
//        return restaurantId;
//    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

//    public void setRestaurant(Restaurant restaurantId) {
//        this.restaurantId = restaurantId;
//    }
}
