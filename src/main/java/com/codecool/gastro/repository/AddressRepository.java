package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    @Query("select a from Address a left join fetch a.restaurant where a.id = :id ")
    Optional<Address> findById(UUID id);

    @Query("select a from Address a left join fetch a.restaurant where a.restaurant.id = :restaurantId")
    Optional<Address> findByRestaurantId(@Param("restaurantId") UUID restaurantId);

}
