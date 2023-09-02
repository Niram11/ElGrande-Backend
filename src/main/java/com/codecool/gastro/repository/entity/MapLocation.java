package com.codecool.gastro.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class MapLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Digits(message = "Max 4 number before \",\"", integer = 4, fraction = 10)
    @NotNull
    private BigDecimal latitude;
    @Digits(message = "Max 4 number before \",\"", integer = 4, fraction = 10)
    @NotNull
    private BigDecimal longitude;

    public MapLocation() {
    }

    public MapLocation(UUID id, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
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
}
