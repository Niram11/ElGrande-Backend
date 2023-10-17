package com.codecool.gastro.repository.specification;

import com.codecool.gastro.repository.entity.Address;
import com.codecool.gastro.repository.entity.Restaurant;
import jakarta.persistence.criteria.*;

import java.util.UUID;

public class RestaurantPredicateByCity implements RestaurantPredicate<String>{
    @Override
    public Predicate predicate(Root<Restaurant> root, CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, String city) {
        Subquery<UUID> citySubQuery = addressSubQuery(criteriaQuery, criteriaBuilder, city);
        return root.get("id").in(citySubQuery);
    }

    private Subquery<UUID> addressSubQuery(CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder,
                                           String city) {
        Subquery<UUID> subquery = criteriaQuery.subquery(UUID.class);
        Root<Address> addressRoot = subquery.from(Address.class);
        subquery.select(addressRoot.get("restaurant").<UUID>get("id"));
        subquery.where(criteriaBuilder.equal(addressRoot.get("city"), city));
        return subquery;
    }
}
