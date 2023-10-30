package com.codecool.gastro.repository;


import com.codecool.gastro.repository.entity.RestaurantCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, UUID> {

    @Query("SELECT rc FROM RestaurantCategory rc")
    List<RestaurantCategory> findAll();
}