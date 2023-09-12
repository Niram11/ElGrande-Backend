package com.codecool.gastro.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min=3)
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Email
    private String email;

    private LocalDate submissionTime;

    @NotBlank
    private String passwordHash;
    @OneToMany
    private final Set<Restaurant> restaurants = new HashSet<>();

    private Boolean isDeleted = false;

    public Customer() {
    }

    public Customer(UUID id, String name, String surname, String email, LocalDate submissionTime, String passwordHash, Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.submissionTime = submissionTime;
        this.passwordHash = passwordHash;
        this.isDeleted = isDeleted;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(LocalDate submissionTime) {
        this.submissionTime = submissionTime;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}


