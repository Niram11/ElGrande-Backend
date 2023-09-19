package com.codecool.gastro.repository.projection;

import java.math.BigDecimal;
import java.util.UUID;

public interface DetailedRestaurantProjection {
    UUID getId();
    String getName();
    String getDescription();
    String getWebsite();
    Integer getContactNumber();
    String getContactEmail();
    String[] getImagesPaths();
    BigDecimal getAverageGrade();
    void setId(UUID id);
    void setName(String name);
    void setDescription(String description);
    void setWebsite(String website);
    void setContactNumber(Integer contactNumber);
    void setContactEmail(String contactEmail);
    void setImagesPaths(String[] imagesPaths);
    void setAverageGrade(BigDecimal averageGrade);
}
