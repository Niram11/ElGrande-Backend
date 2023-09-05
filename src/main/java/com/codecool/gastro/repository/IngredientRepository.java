package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {

    @Query("select i from Ingredient i where i.id = :id")
    Optional<Ingredient> findOneById(UUID id);

    @Query("select i from Ingredient i where i.name = :name ")
    Optional<Ingredient> findByName(String name);
}
