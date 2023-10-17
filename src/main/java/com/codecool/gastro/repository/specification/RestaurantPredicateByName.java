package com.codecool.gastro.repository.specification;

import com.codecool.gastro.dto.criteria.FilteredRestaurantsCriteria;
import com.codecool.gastro.repository.entity.Restaurant;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class RestaurantPredicateByName implements RestaurantPredicate{

    @Override
    public Predicate predicate(Root<Restaurant> root, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, FilteredRestaurantsCriteria filteredRestaurantsCriteria) {
        if (!filteredRestaurantsCriteria.name().equals(null) && !filteredRestaurantsCriteria.name().isEmpty()) {
            return criteriaBuilder.like(root.get("name"), "%" + filteredRestaurantsCriteria.name() + "%");
        }
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }
}
