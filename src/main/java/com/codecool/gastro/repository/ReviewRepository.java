package com.codecool.gastro.repository;


import com.codecool.gastro.repository.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    @Query("select rev from Review rev")
    List<Review> findAll();

    @Query("select rev from Review rev where rev.id = :id")
    Optional<Review> findById(UUID id);
}