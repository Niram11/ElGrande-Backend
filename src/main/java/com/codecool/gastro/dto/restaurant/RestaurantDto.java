package com.codecool.gastro.dto.restaurant;

import com.codecool.gastro.dto.businesshour.BusinessHourDto;
import com.codecool.gastro.dto.image.ImageDto;
import com.codecool.gastro.dto.restaurantcategory.RestaurantCategoryDto;
import com.codecool.gastro.dto.restaurantmenu.RestaurantMenuDto;
import com.codecool.gastro.dto.review.ReviewDto;

import java.util.Set;
import java.util.UUID;

public record RestaurantDto(
        UUID id,
        String name,
        String description,
        String website,
        Integer contactNumber,
        String contactEmail
) {

}
