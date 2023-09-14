package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Ownership;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnershipRepository extends JpaRepository<Ownership, UUID>
{
    @Query("SELECT ownership from Ownership ownership")
    List<Ownership> findAll();

    @Query("SELECT ownership from Ownership ownership where ownership.id = :id")
    Optional<Ownership> findById(UUID id);
}
