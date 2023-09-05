package com.codecool.gastro.repository.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank(message = "Review cannot be empty")
    private String review;
    @Min(1)
    @Max(10)
    private BigDecimal grade;
//    private User user;
//    private Restaurant restaurant;

    public Review(){
    }

    public UUID getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public BigDecimal getGrade(){
        return grade;
    }

//    public User getUserUUID(){
//        return user;
//    }
//
//    public Restaurant getRestaurantUUID() {
//        return restaurant;
//    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public void setRestaurant(Restaurant restaurant) {
//        this.restaurant = restaurant;
//    }
}