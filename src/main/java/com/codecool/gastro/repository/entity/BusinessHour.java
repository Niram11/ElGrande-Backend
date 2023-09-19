package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

@Entity
public class BusinessHour {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Min(value = 1, message = "Day of week must be greater then or equal 1")
    @Max(value = 7, message = "Day of week must be less then or equal 7")
    @NotNull(message = "Day of week cannot be null")
    @Column(nullable = false)
    private Integer dayOfWeek;

    @NotNull(message = "Opening hour cannot be empty")
    @Column(nullable = false)
    private LocalTime openingHour;

    @NotNull(message = "Closing hour cannot be empty")
    @Column(nullable = false)
    private LocalTime closingHour;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    public BusinessHour() {
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
