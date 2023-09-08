package com.codecool.gastro.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Restaurant {
    //TODO: Value objects hibernate
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Size(min = 4, max = 100, message = "Name must be between 4 and 100 characters long")
    private String name;
    @NotBlank(message = "Description can not be empty")
    private String description;
    private String website;
    @Digits(integer = 9, fraction = 0, message = "Number must be 9 digits long")
    private Integer contactNumber;
    @Email(message = "Invalid email")
    private String contactEmail;
    private Boolean isDeleted = false;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn
    private Location location;
    @OneToOne(mappedBy = "restaurant")
    private Address address;
    @OneToOne(mappedBy = "restaurant")
    private PromotedLocal promotedLocal;
    @ManyToMany
    @JoinTable(name = "restaurant_restaurant_category",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_category_id"))
    private final Set<RestaurantCategory> categories = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    private final Set<BusinessHour> businessHours = new HashSet<>();
    @OneToMany(mappedBy = "restaurant")
    private final Set<Image> images = new HashSet<>();
    @OneToMany(mappedBy = "restaurant")
    private final Set<RestaurantMenu> restaurantMenus = new HashSet<>();
    @OneToMany(mappedBy = "restaurant")
    private final Set<Review> reviews = new HashSet<>();

    public Restaurant() {
    }

    public Restaurant(UUID id, String name, String description, String website, Integer contactNumber, String contactEmail, Boolean isDeleted, Customer customer, Location location, Address address, PromotedLocal promotedLocal) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.contactNumber = contactNumber;
        this.contactEmail = contactEmail;
        this.isDeleted = isDeleted;
        this.customer = customer;
        this.location = location;
        this.address = address;
        this.promotedLocal = promotedLocal;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PromotedLocal getPromotedLocal() {
        return promotedLocal;
    }

    public void setPromotedLocal(PromotedLocal promotedLocal) {
        this.promotedLocal = promotedLocal;
    }

    public Set<RestaurantCategory> getCategories() {
        return categories;
    }

    public Set<BusinessHour> getBusinessHours() {
        return businessHours;
    }

    public Set<Image> getImages() {
        return images;
    }

    public Set<RestaurantMenu> getRestaurantMenus() {
        return restaurantMenus;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

}
