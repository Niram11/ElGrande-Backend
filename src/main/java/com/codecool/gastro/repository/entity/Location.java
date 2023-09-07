package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private double latitude;
    private double longitude;
    //TODO:(Niram11) uncomment restaurant column when class Restaurant were created
//    @OneToMany
//    @JoinColumn(name = "restaurant_id", nullable = false)
//    private Restaurant restaurant_UUID;

    public Location() {
    }

//    add Restaurant restaurant_UUID
    public Location(UUID id, double latitude, double longitude) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
//    this.restaurant_UUID = restaurant_UUID;
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

    //TODO:(Niram11) uncomment restaurant column when class Restaurant were created
//    public Restaurant getRestaurantUUID() {
//        return restaurant_UUID;
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

    //TODO:(Niram11) uncomment restaurant column when class Restaurant were created
//    public void setRestaurant(Restaurant restaurant_UUID) {
//        this.restaurant_UUID = restaurant_UUID;
//    }
}