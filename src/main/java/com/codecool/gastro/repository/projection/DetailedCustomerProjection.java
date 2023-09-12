package com.codecool.gastro.repository.projection;

import com.codecool.gastro.dto.restaurant.RestaurantDto;

import java.util.Set;
import java.util.UUID;

public interface DetailedCustomerProjection {
    UUID getId();

    String getName();

    String getSurname();

    String getEmail();

    UUID[] getRestaurants();

    UUID getOwnershipId();

}
