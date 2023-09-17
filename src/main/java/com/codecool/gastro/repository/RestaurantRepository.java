package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    @Query("SELECT res from Restaurant res where res.isDeleted = false ")
    List<Restaurant> findAll();

    @Query("SELECT res from Restaurant res where res.id = :id and res.isDeleted = false ")
    Optional<Restaurant> findById(UUID id);

}
