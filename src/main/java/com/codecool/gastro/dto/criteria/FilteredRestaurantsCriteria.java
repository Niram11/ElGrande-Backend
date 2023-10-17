package com.codecool.gastro.dto.criteria;

import java.util.List;
//TODO: extend with superclass
public record FilteredRestaurantsCriteria (
        String name,
        List<String> category,
        String city,
        List<String> dishName
) {
}

