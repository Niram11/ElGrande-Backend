package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {

    @Query("select i from Ingredient i left join fetch i.restaurantMenus where i.id = :ingredientId")
    Optional<Ingredient> findOneById(UUID ingredientId);

    @Query("select i from Ingredient i left join fetch i.restaurantMenus where i.name = :name")
    Optional<Ingredient> findByName(String name);
}
