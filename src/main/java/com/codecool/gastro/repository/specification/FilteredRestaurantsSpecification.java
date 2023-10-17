package com.codecool.gastro.repository.specification;

import com.codecool.gastro.dto.criteria.FilteredRestaurantsCriteria;
import com.codecool.gastro.repository.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

public class FilteredRestaurantsSpecification {

    private final EntityManager entityManager;
    private final RestaurantPredicateByName predicateByName = new RestaurantPredicateByName();
    private final RestaurantPredicateByRestaurantCategory predicateByCategory = new RestaurantPredicateByRestaurantCategory();
    private final RestaurantPredicateByCity predicateByCity = new RestaurantPredicateByCity();
    private final RestaurantPredicateByDish predicateByDish = new RestaurantPredicateByDish();

    public FilteredRestaurantsSpecification(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public List<Restaurant> getFilteredRestaurants(FilteredRestaurantsCriteria filteredRestaurantsCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurant> criteriaQuery = criteriaBuilder.createQuery(Restaurant.class);
        Root<Restaurant> root = criteriaQuery.from(Restaurant.class);
        List<Predicate> predicates = new ArrayList<>();

        if (!filteredRestaurantsCriteria.name().equals(null) && !filteredRestaurantsCriteria.name().isEmpty()) {
            predicates.add(predicateByName.predicate(root, criteriaBuilder, criteriaQuery, filteredRestaurantsCriteria.name()));
        }
        if (!filteredRestaurantsCriteria.category().equals(null)) {
            predicates.add(predicateByCategory.predicate(root, criteriaBuilder, criteriaQuery, filteredRestaurantsCriteria.category()));
        }
        if (!filteredRestaurantsCriteria.city().equals(null)) {
            predicates.add(predicateByCity.predicate(root,criteriaBuilder,criteriaQuery, filteredRestaurantsCriteria.city()));
        }
        if (!filteredRestaurantsCriteria.dishName().equals(null)) {
            predicates.add(predicateByDish.predicate(root,criteriaBuilder,criteriaQuery,filteredRestaurantsCriteria.dishName()));
        }
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}