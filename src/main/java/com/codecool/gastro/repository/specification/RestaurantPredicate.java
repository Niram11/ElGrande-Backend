package com.codecool.gastro.repository.specification;

import com.codecool.gastro.dto.criteria.FilteredRestaurantsCriteria;
import com.codecool.gastro.repository.entity.Restaurant;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface RestaurantPredicate {
    Predicate predicate(Root<Restaurant> root, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery,
                        FilteredRestaurantsCriteria filteredRestaurantsCriteria);
}
