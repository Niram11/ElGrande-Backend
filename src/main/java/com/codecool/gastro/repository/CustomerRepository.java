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
    @Query("select cus from Customer cus left join fetch cus.restaurants where cus.email = :email and cus.isDeleted = false ")
    Optional<Customer> findByEmail(String email);

    @Query(nativeQuery = true, value = """
            select cus.id, cus.name, cus.surname, cus.email, cus.submission_time,
            array_agg(cr.restaurants_id) as restaurants, ow.id as ownershipId
            from customer as cus left join ownership as ow on cus.id = ow.customer_id
            left join public.customer_restaurants cr on cus.id = cr.customer_id
            where cus.id = :id group by cus.id, ow.id
            """)
    Optional<DetailedCustomerProjection> findDetailedById(UUID id);

}
