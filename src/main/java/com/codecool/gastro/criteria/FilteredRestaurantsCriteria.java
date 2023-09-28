package com.codecool.gastro.criteria;

import java.util.List;

public record FilteredRestaurantsCriteria (
        List<String> category,
        String city,
        List<String> dishName,
        Double reviewMin,
        Double reviewMax,
        String reviewSort
) {
}

