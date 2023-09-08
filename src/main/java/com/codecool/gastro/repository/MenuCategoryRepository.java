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
    @Query("SELECT menucategory FROM MenuCategory menucategory")
    List<MenuCategory> findALl();
    @Query("SELECT menucategory FROM MenuCategory menucategory WHERE menucategory.id = :id")
    Optional<MenuCategory> findBy(UUID id);
}
