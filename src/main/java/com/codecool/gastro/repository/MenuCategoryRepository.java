package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, UUID>
{
    @Query("SELECT mc FROM MenuCategory mc")
    List<MenuCategory> findALl();
    @Query("SELECT mc FROM MenuCategory mc WHERE mc.id = :id")
    Optional<MenuCategory> findBy(UUID id);
    @Query("SELECT mc FROM MenuCategory mc WHERE mc.category = :category")
    Optional<MenuCategory> findBy(String category);
}
