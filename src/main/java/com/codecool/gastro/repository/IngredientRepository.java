package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {

    @Query("select ingredient from Ingredient ingredient where ingredient.id = :id")
    Optional<Ingredient> findOneById(UUID id);

    @Query("select ingredient from Ingredient ingredient where ingredient.name = :name ")
    Optional<Ingredient> findByName(String name);
}
