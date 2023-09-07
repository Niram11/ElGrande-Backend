package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.MenuCategory;
import com.codecool.gastro.repository.entity.Ownership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnershipRepository extends JpaRepository<Ownership, UUID>
{
    @Query("SELECT ow FROM Ownership ow")
    List<Ownership> findALl();
    @Query("SELECT ow FROM Ownership ow WHERE ow.id = :id")
    Optional<Ownership> findOneBy(UUID id);
}
