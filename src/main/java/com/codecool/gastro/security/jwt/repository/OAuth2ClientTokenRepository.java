package com.codecool.gastro.security.jwt.repository;

import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.security.jwt.entity.OAuth2ClientToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OAuth2ClientTokenRepository extends JpaRepository<OAuth2ClientToken, UUID> {
    @Query("select tok from OAuth2ClientToken tok where tok.id = :id")
    Optional<OAuth2ClientToken> findById(UUID id);

    @Query("select tok from OAuth2ClientToken tok where tok.jSessionId = :jSessionId")
    Optional<OAuth2ClientToken> findByJSessionId(String jSessionId);

    @Query("select tok from OAuth2ClientToken tok where tok.customer.id = :customerId")
    Optional<OAuth2ClientToken> findByCustomerId(UUID customerId);

}
