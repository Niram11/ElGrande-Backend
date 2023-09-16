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
}
