package com.codecool.gastro.repository.specification;

import com.codecool.gastro.dto.criteria.FilteredRestaurantsCriteria;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.entity.RestaurantCategory;
import jakarta.persistence.criteria.*;

import java.util.List;
import java.util.UUID;

public class RestaurantPredicateByRestaurantCategory implements RestaurantPredicate {

    @Override
    public Predicate predicate(Root<Restaurant> root, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery,
                               FilteredRestaurantsCriteria filteredRestaurantsCriteria) {
        if (!filteredRestaurantsCriteria.category().equals(null)) {
            Subquery<UUID> categorySubQuery = categorySubQuery(criteriaQuery, filteredRestaurantsCriteria.category());
            return root.get("id").in(categorySubQuery);
        }
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    private Subquery<UUID> categorySubQuery(CriteriaQuery<?> criteriaQuery, List<String> category) {
        Subquery<UUID> subquery = criteriaQuery.subquery(UUID.class);
        Root<RestaurantCategory> categoryRoot = subquery.from(RestaurantCategory.class);
        subquery.select(categoryRoot.get("restaurants").<UUID>get("id"));
        subquery.where(categoryRoot.get("category").in(category));
        return subquery;
    }
}
