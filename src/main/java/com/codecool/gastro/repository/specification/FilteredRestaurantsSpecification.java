package com.codecool.gastro.repository.specification;

import com.codecool.gastro.dto.criteria.FilteredRestaurantsCriteria;
import com.codecool.gastro.repository.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FilteredRestaurantsSpecification {

    private final EntityManager entityManager;

    public FilteredRestaurantsSpecification(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Restaurant> getFilteredRestaurants(FilteredRestaurantsCriteria filteredRestaurantsCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurant> criteriaQuery = criteriaBuilder.createQuery(Restaurant.class);
        Root<Restaurant> root = criteriaQuery.from(Restaurant.class);
        List<Predicate> predicates = new ArrayList<>();

        Subquery<Double> averageRatingSubQuery = averageRatingSubQuery(criteriaQuery, root, criteriaBuilder);

        if (filteredRestaurantsCriteria.name() != null && !filteredRestaurantsCriteria.name().isEmpty()) {
            predicates.add(namePredicate(root, criteriaBuilder, filteredRestaurantsCriteria.name()));
        }

        if (filteredRestaurantsCriteria.category() != null) {
            predicates.add(categoryPredicate(root, criteriaQuery, filteredRestaurantsCriteria.category()));
        }
        if (filteredRestaurantsCriteria.city() != null) {
            predicates.add(cityPredicate(root, criteriaQuery, criteriaBuilder, filteredRestaurantsCriteria.city()));
        }
        if (filteredRestaurantsCriteria.dishName() != null) {
            predicates.add(dishPredicate(root, criteriaQuery, filteredRestaurantsCriteria.dishName()));
        }
        if (filteredRestaurantsCriteria.reviewMin() != null) {
            predicates.add(reviewRangePredicate(criteriaBuilder, averageRatingSubQuery,
                    filteredRestaurantsCriteria.reviewMin()));
        }
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private Predicate namePredicate(Root<Restaurant> root, CriteriaBuilder criteriaBuilder, String name) {
        return criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    private Predicate categoryPredicate(Root<Restaurant> root, CriteriaQuery<Restaurant> criteriaQuery,
                                        List<String> category) {
        Subquery<UUID> categorySubQuery = categorySubQuery(criteriaQuery, category);
        return root.get("id").in(categorySubQuery);
    }

    private Predicate cityPredicate(Root<Restaurant> root, CriteriaQuery<Restaurant> criteriaQuery,
                                    CriteriaBuilder criteriaBuilder, String city) {
        Subquery<UUID> citySubQuery = addressSubQuery(criteriaQuery, criteriaBuilder, city);
        return root.get("id").in(citySubQuery);
    }

    private Predicate dishPredicate(Root<Restaurant> root, CriteriaQuery<Restaurant> criteriaQuery,
                                    List<String> dishName) {
        Subquery<UUID> dishSubQuery = dishSubQuery(criteriaQuery, dishName);
        return root.get("id").in(dishSubQuery);
    }

    private Predicate reviewRangePredicate(CriteriaBuilder criteriaBuilder, Subquery<Double> reviewSubQuery,
                                           Double reviewMin) {
            return criteriaBuilder.greaterThanOrEqualTo(reviewSubQuery, reviewMin);
    }

    private Subquery<UUID> categorySubQuery(CriteriaQuery<?> criteriaQuery, List<String> category) {
        Subquery<UUID> subquery = criteriaQuery.subquery(UUID.class);
        Root<RestaurantCategory> categoryRoot = subquery.from(RestaurantCategory.class);
        subquery.select(categoryRoot.get("restaurants").<UUID>get("id"));
        subquery.where(categoryRoot.get("category").in(category));
        return subquery;
    }

    private Subquery<UUID> addressSubQuery(CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder,
                                           String city) {
        Subquery<UUID> subquery = criteriaQuery.subquery(UUID.class);
        Root<Address> addressRoot = subquery.from(Address.class);
        subquery.select(addressRoot.get("restaurant").<UUID>get("id"));
        subquery.where(criteriaBuilder.equal(addressRoot.get("city"), city));
        return subquery;
    }

    private Subquery<UUID> dishSubQuery(CriteriaQuery<?> criteriaQuery, List<String> dishName) {
        Subquery<UUID> subquery = criteriaQuery.subquery(UUID.class);
        Root<Dish> dishRoot = subquery.from(Dish.class);
        subquery.select(dishRoot.get("restaurant").<UUID>get("id"));
        subquery.where(dishRoot.get("dishName").in(dishName));
        return subquery;
    }

    private Subquery<Double> averageRatingSubQuery(CriteriaQuery<?> criteriaQuery, Root<Restaurant> root,
                                                   CriteriaBuilder criteriaBuilder) {
        Subquery<Double> reviewSubQuery = criteriaQuery.subquery(Double.class);
        Root<Review> reviewRoot = reviewSubQuery.from(Review.class);
        Expression<Double> averageRating = criteriaBuilder.avg(reviewRoot.get("grade"));
        Expression<Double> coalescedRating = criteriaBuilder.coalesce(averageRating, 0.0);
        reviewSubQuery.select(coalescedRating);
        reviewSubQuery.where(criteriaBuilder.equal(reviewRoot.get("restaurant"), root));
        return reviewSubQuery;
    }
}