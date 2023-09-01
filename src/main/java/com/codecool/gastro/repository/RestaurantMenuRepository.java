package com.codecool.gastro.repository;


import com.codecool.gastro.repository.entity.RestaurantMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, UUID> {


    @Query("select rm from RestaurantMenu rm left join fetch rm.ingredients where rm.id = :restaurantMenuId")
    Optional<RestaurantMenu> findOneById(UUID restaurantMenuId);
}
