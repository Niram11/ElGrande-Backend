package com.codecool.gastro.repository.specification;

import com.codecool.gastro.repository.entity.Dish;
import com.codecool.gastro.repository.entity.Restaurant;
import jakarta.persistence.criteria.*;

import java.util.List;
import java.util.UUID;

public class RestaurantPredicateByDish implements RestaurantPredicate<List<String>>{
    @Override
    public Predicate predicate(Root<Restaurant> root, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, List<String> dishName) {
        Subquery<UUID> dishSubQuery = dishSubQuery(criteriaQuery, dishName);
        return root.get("id").in(dishSubQuery);
    }

    private Subquery<UUID> dishSubQuery(CriteriaQuery<?> criteriaQuery, List<String> dishName) {
        Subquery<UUID> subquery = criteriaQuery.subquery(UUID.class);
        Root<Dish> dishRoot = subquery.from(Dish.class);
        subquery.select(dishRoot.get("restaurant").<UUID>get("id"));
        subquery.where(dishRoot.get("dishName").in(dishName));
        return subquery;
    }
}
