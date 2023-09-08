package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;
import java.util.UUID;

@Entity
public class BusinessHour {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Min(value = 1, message = "Number must be greater then or equal 1")
    @Max(value = 7, message = "Number must be less then or equal 10")
    private Integer dayOfWeek;

    private LocalTime openingHour;

    private LocalTime closingHour;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    public BusinessHour() {
    }

    public BusinessHour(UUID id, Integer dayOfWeek, LocalTime openingHour, LocalTime closingHour) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(LocalTime openingHour) {
        this.openingHour = openingHour;
    }

    public LocalTime getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(LocalTime closingHour) {
        this.closingHour = closingHour;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
