package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    @Query("select a from Address a")
    List<Address> findAll();
    @Query("select a from Address a where a.id = :id")
    Optional<Address> findOneBy(UUID id);
}
