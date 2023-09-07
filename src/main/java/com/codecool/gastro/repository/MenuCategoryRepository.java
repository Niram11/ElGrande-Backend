package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, UUID>
{
    @Query("SELECT c FROM MenuCategory c")
    List<MenuCategory> findALl();
    @Query("SELECT c FROM MenuCategory c WHERE c.id = :id")
    Optional<MenuCategory> findOneBy(UUID id);
}
