package com.codecool.gastro.security.jwt.service;

import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.security.jwt.dto.TokenRefreshRequest;
import com.codecool.gastro.security.jwt.dto.TokenRefreshResponse;
import com.codecool.gastro.security.jwt.entity.RefreshToken;
import com.codecool.gastro.security.jwt.repository.RefreshTokenRepository;
import com.codecool.gastro.security.jwt.service.exception.TokenRefreshException;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class RefreshTokenService {
    @Value("${gastro.app.jwtRefreshTokenExpirationMs}")
    private Long refreshTokenDurationMs;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomerRepository customerRepository;
    private final JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

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
                    deleteByCustomerId(customer.getId());
                    RefreshToken newRefreshToken = createRefreshToken(customer.getId());
                    String token = jwtUtils.generateTokenFromEmail(customer.getEmail());
                    return new TokenRefreshResponse(token, newRefreshToken.getToken(), "Bearer");
                })
                .orElseThrow(() -> new TokenRefreshException(tokenRefreshRequest.token()));

    }

    public RefreshToken createRefreshToken(UUID customerId) {
        refreshTokenRepository.findByCustomerId(customerId)
                .ifPresent(refreshToken -> {
                    logger.error("Refresh accessToken already exist");
                    throw new TokenRefreshException(refreshToken.getToken());
                });

        logger.info("Creating new refresh accessToken");
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCustomer(customerRepository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException(customerId, Customer.class)));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        logger.info("Saving refresh accessToken");
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        logger.info("Verifying refresh accessToken...");
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            logger.error("Refresh accessToken expired");
            throw new TokenRefreshException(token.getToken());
        }
        logger.info("Refresh accessToken is valid");
        return token;
    }

    public void deleteByCustomerId(UUID customerId) {
        refreshTokenRepository.findByCustomerId(customerId).ifPresent(refreshTokenRepository::delete);
    }
}
