package com.codecool.gastro.security.jwt.service;

import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.security.jwt.entity.RefreshToken;
import com.codecool.gastro.security.jwt.repository.RefreshTokenRepository;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.exception.TokenAlreadyExistException;
import com.codecool.gastro.service.exception.TokenRefreshException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${gastro.app.jwtRefreshTokenExpirationMs}")
    private Long refreshTokenDurationMs;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomerRepository customerRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, CustomerRepository customerRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.customerRepository = customerRepository;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(UUID customerId) {
        deleteTokenOnValidation(customerId);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCustomer(customerRepository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException(customerId, Customer.class)));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken());
        }
        return token;
    }

    public void deleteTokenOnValidation(UUID customerId) {
        refreshTokenRepository.findByCustomerId(customerId)
                .ifPresent(rt -> {
                    throw new TokenAlreadyExistException(customerId);
                });
    }

    @Transactional
    public void deleteByCustomerId(UUID customerId) {
        refreshTokenRepository.deleteByCustomer(customerRepository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException(customerId, Customer.class)));
    }
}
