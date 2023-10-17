package com.codecool.gastro.dto.criteria;

import java.util.List;
//TODO: extend with superclass
public record FilteredRestaurantsCriteria (
        //TODO: convert name to list and change specification
        String name,
        List<String> category,
        String city,
        List<String> dishName
) {
}

