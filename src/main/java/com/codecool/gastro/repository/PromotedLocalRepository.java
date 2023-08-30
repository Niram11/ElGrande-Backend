package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.PromotedLocal;
import com.codecool.gastro.repository.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PromotedLocalRepository extends JpaRepository<PromotedLocal, UUID> {
    List<PromotedLocal> findAllBy();
    @Query("SELECT pl FROM PromotedLocal pl WHERE pl.id = :id")
    Optional<PromotedLocal> findBy(UUID id);

}
