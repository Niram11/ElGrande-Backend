package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.DishCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DishCategoryRepository extends JpaRepository<DishCategory, UUID> {
    @Query("SELECT dishCategory FROM DishCategory dishCategory")
    List<DishCategory> findAll();

    @Query("SELECT dishCategory FROM DishCategory dishCategory WHERE dishCategory.id = :id")
    Optional<DishCategory> findById(UUID id);

    @Query("SELECT dishCategory FROM DishCategory dishCategory WHERE dishCategory.category = :category")
    Optional<DishCategory> findByCategory(String category);
}
