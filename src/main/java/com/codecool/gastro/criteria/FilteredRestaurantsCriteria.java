package com.codecool.gastro.criteria;

import java.util.Collections;
import java.util.List;

public record FilteredRestaurantsCriteria(List<String> category, String city, List<String> dishName, Double reviewMin, Double reviewMax, String reviewSort) {
    public FilteredRestaurantsCriteria(List<String> category, String city, List<String> dishName, Double reviewMin,
                                       Double reviewMax, String reviewSort) {
        this.category = category;
        this.city = city;
        this.dishName = dishName;
        if (reviewMin == null || reviewMin < 0 || reviewMin > 10) {
            this.reviewMin = Double.valueOf(0);
        } else {
            this.reviewMin = reviewMin;
        }
        if (reviewMax == null || reviewMax < 0 || reviewMax > 10) {
            this.reviewMax = Double.valueOf(10);
        } else {
            this.reviewMax = reviewMax;
        }
        if(reviewSort == "DESC") {
            this.reviewSort = reviewSort;
        } else {
            this.reviewSort = "ASC";
        }
    }
}
