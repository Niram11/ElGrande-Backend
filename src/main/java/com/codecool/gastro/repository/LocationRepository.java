package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Location;
import com.codecool.gastro.repository.entity.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

    @Query("select location from Location location left join fetch location.restaurants")
    List<Location> findAll();

    @Query("select location from Location location left join fetch location.restaurants where location.id =:id")
    Optional<Location> findById(UUID id);
}