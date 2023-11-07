package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

    @Query("select location from Location location left join fetch location.restaurants")
    List<Location> findAll();

    @Query("select location from Location location left join fetch location.restaurants where location.id =:id")
    Optional<Location> findById(UUID id);

    @Query("select loc from Location loc left join fetch loc.restaurants " +
            "where loc.latitude = :latitude and loc.longitude = :longitude")
    Optional<Location> findByCoordinates(BigDecimal latitude, BigDecimal longitude);
}