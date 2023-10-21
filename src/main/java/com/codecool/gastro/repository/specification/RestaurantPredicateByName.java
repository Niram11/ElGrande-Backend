package com.codecool.gastro.repository.specification;

import com.codecool.gastro.dto.criteria.FilteredRestaurantsCriteria;
import com.codecool.gastro.repository.entity.Restaurant;
import jakarta.persistence.criteria.*;

import java.util.List;

public class RestaurantPredicateByName implements RestaurantPredicate{

    @Override
    public Predicate predicate(Root<Restaurant> root, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery,
                               FilteredRestaurantsCriteria filteredRestaurantsCriteria) {
        if (filteredRestaurantsCriteria.name() != null && !filteredRestaurantsCriteria.name().isEmpty()) {
            List<String> names = filteredRestaurantsCriteria.name();

            Expression<String> nameExpression = root.get("name");
            Predicate inPredicate = nameExpression.in(filteredRestaurantsCriteria.name());

            return inPredicate;
        }

        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }
}
