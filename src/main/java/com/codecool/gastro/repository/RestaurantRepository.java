package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.projection.DetailedRestaurantProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("select res from Restaurant res where res.name = :name")
    Optional<Restaurant> findByName(String name);

    @Query(value = "SELECT " +
            "restaurant.id AS id, restaurant.name, restaurant.description, restaurant.website," +
            "restaurant.contact_number AS contactNumber, restaurant.contact_email AS contactEmail," +
            "ARRAY_AGG(image.path_to_image) AS imagesPaths ," +
            "ROUND(AVG(review.grade),2) AS averageGrade " +
            "FROM " +
            "restaurant restaurant " +
            "LEFT JOIN " +
            "image image ON restaurant.id = image.restaurant_id " +
            "LEFT JOIN " +
            "review review ON restaurant.id = review.restaurant_id " +
            "GROUP BY " +
            "review.grade, restaurant.id, restaurant.contact_number, restaurant.is_deleted " +
            "ORDER BY " +
            "CASE WHEN review.grade IS NULL THEN 1 ELSE 0 END, " +
            "averageGrade DESC " +
            "LIMIT :quantity", nativeQuery = true)
    List<DetailedRestaurantProjection> getTopRestaurants(@Param("quantity") int quantity);
}
