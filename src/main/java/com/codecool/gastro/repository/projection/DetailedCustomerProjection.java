package com.codecool.gastro.repository.projection;


import java.time.LocalDate;
import java.util.UUID;

public interface DetailedCustomerProjection {
    UUID getId();

    String getName();

    String getSurname();

    String getEmail();

    LocalDate getSubmissionTime();

    UUID[] getRestaurants();

    UUID getOwnershipId();

    void setId(UUID id);

    void setName(String name);

    void setSurname(String surname);

    void setEmail(String email);

    void setSubmissionTime(LocalDate submissionTime);

    void setRestaurants(UUID[] restaurants);

    void setOwnershipId(UUID ownershipId);
}
