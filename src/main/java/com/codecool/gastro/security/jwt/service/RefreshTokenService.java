package com.codecool.gastro.security.jwt.service;

import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.security.jwt.dto.TokenRefreshRequest;
import com.codecool.gastro.security.jwt.dto.TokenRefreshResponse;
import com.codecool.gastro.security.jwt.entity.RefreshToken;
import com.codecool.gastro.security.jwt.repository.RefreshTokenRepository;
import com.codecool.gastro.security.jwt.service.exception.TokenAlreadyExistException;
import com.codecool.gastro.security.jwt.service.exception.TokenRefreshException;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${gastro.app.jwtRefreshTokenExpirationMs}")
    private Long refreshTokenDurationMs;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomerRepository customerRepository;
    private final JwtUtils jwtUtils;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, CustomerRepository customerRepository, JwtUtils jwtUtils) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.customerRepository = customerRepository;
        this.jwtUtils = jwtUtils;
    }


    public TokenRefreshResponse handleRefreshTokenRequest(TokenRefreshRequest tokenRefreshRequest) {
        return refreshTokenRepository.findByToken(tokenRefreshRequest.token())
                .map(this::verifyExpiration)
                .map(RefreshToken::getCustomer)
                .map(customer -> {
                    String token = jwtUtils.generateTokenFromEmail(customer.getEmail());
                    deleteByCustomerId(customer.getId());
                    RefreshToken newRefreshToken = createRefreshToken(customer.getId());
                    return new TokenRefreshResponse(token, newRefreshToken.getToken(), "Bearer");
                })
                .orElseThrow(() -> new TokenRefreshException(tokenRefreshRequest.token()));

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

    public void deleteByCustomerId(UUID customerId) {
        refreshTokenRepository.findByCustomerId(customerId)
                .ifPresent(refreshTokenRepository::delete);
    }
}
