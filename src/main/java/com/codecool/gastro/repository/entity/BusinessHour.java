package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.sql.Time;
import java.util.UUID;

@Entity
public class BusinessHour {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    private Integer dayOfWeek;
    @NotBlank
    private Time openingHour;
    @NotBlank
    private Time closingHour;
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public BusinessHour() {
    }

    public BusinessHour(UUID id, Integer dayOfWeek, Time openingHour, Time closingHour) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
    }
}
