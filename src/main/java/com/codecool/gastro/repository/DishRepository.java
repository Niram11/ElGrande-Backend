package com.codecool.gastro.repository;


import com.codecool.gastro.repository.entity.Dish;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DishRepository extends JpaRepository<Dish, UUID> {

    @EntityGraph(attributePaths = {"ingredients", "categories"})
    List<Dish> findAll();


    @EntityGraph(attributePaths = {"ingredients", "categories"})
    Optional<Dish> findById(UUID id);

    @EntityGraph(attributePaths = {"restaurant"})
    List<Dish> findByRestaurantId(UUID restaurantId);
}
