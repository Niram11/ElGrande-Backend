package com.codecool.gastro.repository;

import com.codecool.gastro.repository.projection.DetailedCustomerProjection;
import com.codecool.gastro.repository.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Query("SELECT cus from Customer cus left join fetch cus.restaurants where cus.isDeleted = false ")
    List<Customer> findAll();

    @Query("SELECT cus FROM Customer cus left join fetch cus.restaurants WHERE cus.id = :id AND cus.isDeleted = false ")
    Optional<Customer> findById(UUID id);

    @Query(nativeQuery = true, value = "select cus.id, cus.name, cus.surname, cus.email, " +
            "array_agg(cr.restaurants_id) as restaurants, ow.id as ownershipId " +
            "from customer as cus left join ownership as ow on cus.id = ow.customer_id " +
            "left join public.customer_restaurants cr on cus.id = cr.customer_id " +
            "group by cus.id, ow.id")
    Optional<DetailedCustomerProjection> findDetailedById(UUID id);

}
