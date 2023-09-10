package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    @Query("SELECT res FROM Restaurant res left join fetch res.categories left join fetch res.businessHours " +
            "left join fetch res.images left join fetch res.restaurantMenus left join fetch res.reviews")
    List<Restaurant> findAll();
    @Query("SELECT res FROM Restaurant res left join fetch res.categories left join fetch res.businessHours " +
            "left join fetch res.images left join fetch res.restaurantMenus left join fetch res.reviews " +
            "WHERE res.id = :id")
    Optional<Restaurant> findBy(UUID id);

}
