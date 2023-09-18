package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.projection.DetailedRestaurantProjection;
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


    @Query(value =
            "SELECT " +
                    "    res_data.id, res_data.name, res_data.description, res_data.website," +
                    "    res_data.contactNumber, res_data.contactEmail," +
                    "    COALESCE(res_data.imagesPaths, '{}') AS imagesPaths," +
                    "    COALESCE(rev_data.averageGrade, NULL) AS averageGrade " +
                    "FROM (" +
                    "    SELECT " +
                    "        res.id, res.name, res.description, res.website," +
                    "        res.contact_number AS contactNumber, res.contact_email AS contactEmail," +
                    "        ARRAY_AGG(DISTINCT ima.path_to_image) FILTER (WHERE ima.path_to_image IS NOT NULL) AS imagesPaths " +
                    "    FROM restaurant res " +
                    "    LEFT JOIN image ima ON res.id = ima.restaurant_id " +
                    "    GROUP BY res.id " +
                    ") AS res_data " +
                    "LEFT JOIN (" +
                    "    SELECT " +
                    "        res.id AS id, AVG(rev.grade) AS averageGrade " +
                    "    FROM restaurant res " +
                    "    LEFT JOIN review rev ON res.id = rev.restaurant_id " +
                    "    GROUP BY res.id " +
                    ") AS rev_data ON res_data.id = rev_data.id " +
                    "ORDER BY " +
                    "    CASE WHEN averageGrade IS NULL THEN 1 ELSE 0 END, " +
                    "    averageGrade DESC " +
                    "LIMIT :quantity", nativeQuery = true)
    List<DetailedRestaurantProjection> getTopRestaurants(@Param("quantity") int quantity);

}
