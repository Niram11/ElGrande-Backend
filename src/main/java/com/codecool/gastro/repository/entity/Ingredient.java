package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.UUID;

@Entity
public class Ingredient {

    public static final String REGEX_FOR_INGREDIENT = "^[a-zA-Z][a-zA-Z ]*$";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Name cannot be empty")
    @Pattern(regexp = REGEX_FOR_INGREDIENT,
            message = "Name must contain only letters and not start with number or whitespace")
    @Column(unique = true)
    private String name;

    public Ingredient() {
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
