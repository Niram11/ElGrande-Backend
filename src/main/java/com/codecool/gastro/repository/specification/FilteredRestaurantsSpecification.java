package com.codecool.gastro.repository.specification;

import com.codecool.gastro.dto.criteria.FilteredRestaurantsCriteria;
import com.codecool.gastro.repository.entity.Restaurant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

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
        addPredicates(predicates, root, criteriaBuilder, criteriaQuery, filteredRestaurantsCriteria);

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private void addPredicates(List<Predicate> predicates, Root<Restaurant> root,
                               CriteriaBuilder criteriaBuilder,
                               CriteriaQuery<Restaurant> criteriaQuery,
                               FilteredRestaurantsCriteria filteredRestaurantsCriteria) {
        predicates.add(predicateByName.predicate(root, criteriaBuilder, criteriaQuery, filteredRestaurantsCriteria));
        predicates.add(predicateByCategory.predicate(root, criteriaBuilder, criteriaQuery, filteredRestaurantsCriteria));
        predicates.add(predicateByCity.predicate(root, criteriaBuilder, criteriaQuery, filteredRestaurantsCriteria));
        predicates.add(predicateByDish.predicate(root, criteriaBuilder, criteriaQuery, filteredRestaurantsCriteria));
    }
}