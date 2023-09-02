package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.MapLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MapLocationRepository extends JpaRepository<MapLocation, UUID> {

    List<MapLocation> findALl();
    @Query("SELECT ml FROM MapLocation ml WHERE ml.id = :id")
    Optional<MapLocation> findOneBy(UUID id);
}
