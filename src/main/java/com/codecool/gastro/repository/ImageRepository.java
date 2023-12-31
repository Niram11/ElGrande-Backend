package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {

    @Query("SELECT image FROM Image image WHERE image.restaurant.id = :id")
    List<Image> findAllByRestaurant(UUID id);

}
