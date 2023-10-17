package com.codecool.gastro.repository.specification;

import com.codecool.gastro.repository.entity.Restaurant;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class RestaurantPredicateByName implements RestaurantPredicate<String> {

    @Override
    public Predicate predicate(Root<Restaurant> root, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, String name) {
        return criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }
}
