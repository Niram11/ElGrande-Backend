package com.codecool.gastro.service.specification;

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

    public List<Restaurant> getFilteredRestaurants(String category, String city, String dishName, Double reviewMin,
                                                   Double reviewMax, String reviewSort) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurant> criteriaQuery = criteriaBuilder.createQuery(Restaurant.class);
        Root<Restaurant> root = criteriaQuery.from(Restaurant.class);
        List<Predicate> predicates = new ArrayList<>();

        Subquery<Double> averageRatingSubQuery = averageRatingSubQuery(criteriaQuery, root, criteriaBuilder);

        if (category != null) {
            predicates.add(categoryPredicate(root, criteriaBuilder, category));
        }
        if (city != null) {
            predicates.add(cityPredicate(root, criteriaQuery, criteriaBuilder, city));
        }
        if (dishName != null) {
            predicates.add(dishPredicate(root, criteriaQuery, criteriaBuilder, dishName));
        }
        predicates.add(reviewRangePredicate(criteriaBuilder, averageRatingSubQuery, reviewMin, reviewMax));
        Order order = determineOrder(criteriaBuilder, averageRatingSubQuery, reviewSort);

        criteriaQuery.orderBy(order);
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private Predicate categoryPredicate(Root<Restaurant> root, CriteriaBuilder criteriaBuilder, String category) {
        Join<Restaurant, RestaurantCategory> joinCategory = root.join("categories", JoinType.LEFT);
        return criteriaBuilder.equal(joinCategory.get("category"), category);
    }

    private Predicate cityPredicate(Root<Restaurant> root, CriteriaQuery<Restaurant> criteriaQuery,
                                    CriteriaBuilder criteriaBuilder, String city) {
        Subquery<UUID> citySubQuery = addressSubQuery(criteriaQuery, criteriaBuilder, city);
        return root.get("id").in(citySubQuery);
    }

    private Predicate dishPredicate(Root<Restaurant> root, CriteriaQuery<Restaurant> criteriaQuery,
                                    CriteriaBuilder criteriaBuilder, String dishName) {
        Subquery<UUID> dishSubQuery = dishSubQuery(criteriaQuery, criteriaBuilder, dishName);
        return root.get("id").in(dishSubQuery);
    }

    private Predicate reviewRangePredicate(CriteriaBuilder criteriaBuilder, Subquery<Double> reviewSubQuery,
                                           Double reviewMin, Double reviewMax) {
        return criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(reviewSubQuery, reviewMin),
                criteriaBuilder.lessThanOrEqualTo(reviewSubQuery, reviewMax)
        );
    }

    private Order determineOrder(CriteriaBuilder criteriaBuilder, Subquery<Double> averageRatingSubQuery, String reviewSort) {
        if ("ASC".equalsIgnoreCase(reviewSort)) {
            return criteriaBuilder.asc(averageRatingSubQuery);
        } else {
            return criteriaBuilder.desc(averageRatingSubQuery);
        }
    }

    private Subquery<UUID> addressSubQuery(CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder,
                                           String city) {
        Subquery<UUID> subquery = criteriaQuery.subquery(UUID.class);
        Root<Address> addressRoot = subquery.from(Address.class);
        subquery.select(addressRoot.get("restaurant").<UUID>get("id"));
        subquery.where(criteriaBuilder.equal(addressRoot.get("city"), city));
        return subquery;
    }

    private Subquery<UUID> dishSubQuery(CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder,
                                        String dishName) {
        Subquery<UUID> subquery = criteriaQuery.subquery(UUID.class);
        Root<Dish> dishRoot = subquery.from(Dish.class);
        subquery.select(dishRoot.get("restaurant").<UUID>get("id"));
        subquery.where(criteriaBuilder.equal(dishRoot.get("dishName"), dishName));
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