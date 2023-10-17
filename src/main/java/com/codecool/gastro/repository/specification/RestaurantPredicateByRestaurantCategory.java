package com.codecool.gastro.repository.specification;

import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.entity.RestaurantCategory;
import jakarta.persistence.criteria.*;

import java.util.List;
import java.util.UUID;

public class RestaurantPredicateByRestaurantCategory implements RestaurantPredicate<List<String>>{

    @Override
    public Predicate predicate(Root<Restaurant> root, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, List<String> restaurantCategory) {
        Subquery<UUID> categorySubQuery = categorySubQuery(criteriaQuery, restaurantCategory);
        return root.get("id").in(categorySubQuery);
    }

    private Subquery<UUID> categorySubQuery(CriteriaQuery<?> criteriaQuery, List<String> category) {
        Subquery<UUID> subquery = criteriaQuery.subquery(UUID.class);
        Root<RestaurantCategory> categoryRoot = subquery.from(RestaurantCategory.class);
        subquery.select(categoryRoot.get("restaurants").<UUID>get("id"));
        subquery.where(categoryRoot.get("category").in(category));
        return subquery;
    }
}
