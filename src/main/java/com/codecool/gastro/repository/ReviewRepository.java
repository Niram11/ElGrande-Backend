package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Review;
import com.codecool.gastro.repository.projection.DetailedReviewProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    @Query("select rev from Review rev left join fetch rev.customer left join fetch rev.restaurant where rev.id = :id")
    Optional<Review> findById(UUID id);

    @Query("select rev from Review rev " +
            "left join fetch rev.restaurant " +
            "left join fetch rev.customer " +
            "where rev.restaurant.id = :id")
    List<Review> getReviewsByRestaurant(UUID id, Pageable pageable);


    @Query("select rev from Review rev left join fetch rev.restaurant left join fetch rev.customer where rev.customer.id = :id")
    List<Review> getReviewsByCustomerId(UUID id);

    @Query(nativeQuery = true, value = """
            select r.comment, r.grade, c.name, r.submission_time as submissionTime from review r left join customer c on r.customer_id = c.id
            where restaurant_id = :restaurantId
            """)
    List<DetailedReviewProjection> findDetailedReviewsByRestaurantId(UUID restaurantId);
}