package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    @Query("SELECT r FROM Restaurant r")
    List<Restaurant> findAll();
    @Query("SELECT r FROM Restaurant r WHERE r.id = :id")
    Optional<Restaurant> findBy(UUID id);

}
