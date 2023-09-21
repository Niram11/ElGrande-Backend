package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.projection.DetailedRestaurantProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    @Query("SELECT res from Restaurant res where res.isDeleted = false ")
    List<Restaurant> findAll();

    @Query("SELECT res from Restaurant res where res.id = :id and res.isDeleted = false ")
    Optional<Restaurant> findById(UUID id);


    @Query(nativeQuery = true, value = """
            SELECT
                res_data.id,
                res_data.name,
                res_data.description,
                res_data.website,
                res_data.contactNumber,
                res_data.contactEmail,
                COALESCE(res_data.imagesPaths, '{}') AS imagesPaths,
                COALESCE(rev_data.averageGrade, NULL) AS averageGrade
            FROM (
                SELECT
                    res.id,
                    res.name,
                    res.description,
                    res.website,
                    res.contact_number AS contactNumber,
                    res.contact_email AS contactEmail,
                    ARRAY_AGG(DISTINCT ima.path_to_image) FILTER (
                    WHERE
                        ima.path_to_image IS NOT NULL
                  ) AS imagesPaths
                FROM
                    restaurant res
                    LEFT JOIN image ima ON res.id = ima.restaurant_id
                GROUP BY
                    res.id
              ) AS res_data
              LEFT JOIN (
                    SELECT
                        res.id AS id,
                        AVG(rev.grade) AS averageGrade
                    FROM
                        restaurant res
                    LEFT JOIN review rev ON res.id = rev.restaurant_id
                    GROUP BY
                        res.id
              ) AS rev_data ON res_data.id = rev_data.id
            ORDER BY
            CASE WHEN averageGrade IS NULL THEN 1 ELSE 0 END,
            averageGrade DESC
            """)
    List<DetailedRestaurantProjection> findAllDetailedRestaurants(Pageable pageable);




    @Query(value =
            "SELECT * FROM (" +
                    "    SELECT DISTINCT restaurant.id, restaurant.name, restaurant.description, restaurant.website," +
                    "            restaurant.contact_number, restaurant.contact_email, restaurant.is_deleted, avg_reviews.avg_grade," +
                    "            CASE :reviewSort " +
                    "                WHEN 'ASC' THEN avg_reviews.avg_grade " +
                    "                WHEN 'DESC' THEN -avg_reviews.avg_grade " +
                    "            END as sort_column " +
                    "    FROM restaurant " +
                    "    LEFT JOIN address ON restaurant.id = address.restaurant_id " +
                    "    LEFT JOIN restaurant_restaurant_category ON restaurant.id = restaurant_restaurant_category.restaurant_id " +
                    "    LEFT JOIN restaurant_category ON restaurant_restaurant_category.restaurant_category_id = restaurant_category.id " +
                    "    LEFT JOIN dish ON restaurant.id = dish.restaurant_id " +
                    "    LEFT JOIN (SELECT restaurant_id, AVG(grade) as avg_grade FROM review GROUP BY restaurant_id) " +
                    "AS avg_reviews " +
                    "        ON restaurant.id = avg_reviews.restaurant_id " +
                    "    LEFT JOIN promoted_local ON restaurant.id = promoted_local.restaurant_id " +
                    "    WHERE (:city IS NULL OR address.city = :city) " +
                    "    AND (:category IS NULL OR restaurant_category.category = :category) " +
                    "    AND (:dishName IS NULL OR dish.dish_name = :dishName) " +
                    "    AND (:reviewMin IS NULL OR COALESCE(avg_reviews.avg_grade, 0) >= :reviewMin) " +
                    "    AND (:reviewMax IS NULL OR COALESCE(avg_reviews.avg_grade, 0) <= :reviewMax) " +
                    ") AS subquery " +
                    "ORDER BY sort_column",
            nativeQuery = true)
    List<Restaurant> getFilteredRestaurants(
            @Param("category") String category,
            @Param("city") String city,
            @Param("dishName") String dishName,
            @Param("reviewMin") BigDecimal reviewMin,
            @Param("reviewMax") BigDecimal reviewMax,
            @Param("reviewSort") String reviewSort);


}
