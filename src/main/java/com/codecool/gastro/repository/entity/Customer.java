package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Customer implements EntityObject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3)
    private String name;
    private String surname;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email")
    @Column(unique = true)
    private String email;
    private LocalDate submissionTime;
    private String password;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "customer_role",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private final Set<Role> roles = new HashSet<>();
    @OneToMany
    private final Set<Restaurant> restaurants = new HashSet<>();
    private Boolean isDeleted = false;

    public Customer() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
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

    public void assignRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }
    public void assignRole(Role role) {
        roles.add(role);
    }
}


