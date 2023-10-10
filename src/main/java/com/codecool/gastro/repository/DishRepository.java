package com.codecool.gastro.repository;


import com.codecool.gastro.repository.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DishRepository extends JpaRepository<Dish, UUID> {

    @Query("SELECT dish from Dish dish left join fetch dish.categories " +
            "left join fetch dish.ingredients left join fetch dish.restaurant " +
            "WHERE dish.id = :id")
    Optional<Dish> findById(UUID id);

    @Query("SELECT dish from Dish dish LEFT join fetch dish.categories " +
            "left join fetch dish.ingredients left join fetch dish.restaurant " +
            "WHERE dish.restaurant.id = :restaurantId")
    List<Dish> findByRestaurantId(UUID restaurantId);

}
