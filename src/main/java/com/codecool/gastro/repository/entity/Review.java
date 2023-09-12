package com.codecool.gastro.repository.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank(message = "Review cannot be empty")
    private String comment;
    @Min(value = 1, message = "Grade must be greater then or equal 1")
    @Max(value = 10, message = "Grade must be less then or equal 10")
    private BigDecimal grade;

    private LocalDate submissionTime;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    public Review() {
    }

    public Review(UUID id, String comment, BigDecimal grade, LocalDate submissionTime, Customer customer, Restaurant restaurant) {
        this.id = id;
        this.comment = comment;
        this.grade = grade;
        this.submissionTime = submissionTime;
        this.customer = customer;
        this.restaurant = restaurant;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public LocalDate getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(LocalDate submissionTime) {
        this.submissionTime = submissionTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}