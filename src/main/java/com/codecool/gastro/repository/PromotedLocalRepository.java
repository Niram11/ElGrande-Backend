package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.PromotedLocal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PromotedLocalRepository extends JpaRepository<PromotedLocal, UUID> {
    @Query("SELECT pl FROM PromotedLocal pl")
    List<PromotedLocal> findAll();

    @Query("SELECT pl FROM PromotedLocal pl where pl.id = :promotedLocalId")
    Optional<PromotedLocal> findById(@Param("promotedLocalId") UUID promotedLocalId);
}
