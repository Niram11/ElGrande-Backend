package com.codecool.gastro.dto.criteria;

import java.util.List;

public record FilteredRestaurantsCriteria (
        String name,
        List<String> category,
        String city,
        List<String> dishName,
        Double reviewMin
) {
}

