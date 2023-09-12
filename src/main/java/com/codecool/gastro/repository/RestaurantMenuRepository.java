package com.codecool.gastro.repository;


import com.codecool.gastro.repository.entity.RestaurantMenu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, UUID> {

    @EntityGraph(attributePaths = {"ingredients", "categories"})
    List<RestaurantMenu> findAll();


    @EntityGraph(attributePaths = {"ingredients", "categories"})
    Optional<RestaurantMenu> findById(UUID id);
}
