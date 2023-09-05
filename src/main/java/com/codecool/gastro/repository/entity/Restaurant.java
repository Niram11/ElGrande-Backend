package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Size(min = 4, max = 100, message = "Title must be between 4 and 100 characters long")
    private String name;
    @NotBlank
    private String description;
    private String website;
    @Digits(message = "Number must be 15 digits long", integer = 15, fraction = 0)
    private Integer contactNumber;
    @Email
    private String contactEmail;

    public Restaurant(UUID id, String name, String description, String website, Integer contactNumber, String contactEmail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.contactNumber = contactNumber;
        this.contactEmail = contactEmail;
    }

    public Restaurant() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Integer contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
