package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Ownership;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnershipRepository extends JpaRepository<Ownership, UUID>
{
    @EntityGraph(attributePaths = {"customer", "restaurants"})
    List<Ownership> findAll();
    @EntityGraph(attributePaths = {"customer", "restaurants"})
    Optional<Ownership> findById(UUID id);
}
