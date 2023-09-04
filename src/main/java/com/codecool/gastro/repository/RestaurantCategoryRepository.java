package com.codecool.gastro.repository;


import com.codecool.gastro.repository.entity.RestaurantCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, UUID> {
}
