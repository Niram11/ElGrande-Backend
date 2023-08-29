package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationsRepository extends JpaRepository<Locations, UUID> {
}