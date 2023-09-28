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
    @Query("SELECT bh FROM BusinessHour bh left join fetch bh.restaurant order by bh.dayOfWeek")
    List<BusinessHour> findAll();

    @Query("SELECT bh FROM BusinessHour bh left join fetch bh.restaurant WHERE bh.id = :id")
    Optional<BusinessHour> findById(UUID id);

    @Query("SELECT bh from BusinessHour bh left join fetch bh.restaurant where bh.restaurant.id = :restaurantId order by bh.dayOfWeek ")
    List<BusinessHour> findAllByRestaurantId(UUID restaurantId);

}
