package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.BusinessHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessHourRepository extends JpaRepository<BusinessHour, UUID> {
    @Query("SELECT bh FROM BusinessHour bh")
    List<BusinessHour> findAll();
    @Query("SELECT bh FROM BusinessHour bh WHERE bh.id = :id")
    Optional<BusinessHour> findBy(UUID id);
}
