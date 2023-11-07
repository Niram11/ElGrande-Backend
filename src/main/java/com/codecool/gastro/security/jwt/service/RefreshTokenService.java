package com.codecool.gastro.security.jwt.service;

import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.security.jwt.dto.TokenRefreshRequest;
import com.codecool.gastro.security.jwt.dto.TokenRefreshResponse;
import com.codecool.gastro.security.jwt.entity.RefreshToken;
import com.codecool.gastro.security.jwt.repository.RefreshTokenRepository;
import com.codecool.gastro.security.jwt.service.exception.TokenRefreshException;
import com.codecool.gastro.service.validation.CustomerValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final CustomerValidation customerValidation;
    private final JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, CustomerRepository customerRepository, CustomerValidation customerValidation, JwtUtils jwtUtils) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.customerRepository = customerRepository;
        this.customerValidation = customerValidation;
        this.jwtUtils = jwtUtils;
    }


    public TokenRefreshResponse handleRefreshTokenRequest(TokenRefreshRequest tokenRefreshRequest) {
        return refreshTokenRepository.findByToken(tokenRefreshRequest.token())
                .map(refreshToken -> {
                    verifyExpiration(refreshToken);
                    return refreshToken.getCustomer();
                })
                .map(c -> {
                    RefreshToken newRefreshToken = createRefreshToken(c.getId());
                    String token = jwtUtils.generateTokenFromEmail(c.getEmail());
                    return new TokenRefreshResponse(token, newRefreshToken.getToken(), "Bearer");
                })
                .orElseThrow(() -> new TokenRefreshException(tokenRefreshRequest.token()));
    }

    public RefreshToken createRefreshToken(UUID customerId) {
        return refreshTokenRepository.save(refreshTokenRepository.findByCustomerId(customerId)
                .map(this::refreshExistingRefreshToken)
                .orElseGet(() -> createNewRefreshToken(customerId)));
    }

    public void deleteByCustomerId(UUID customerId) {
        refreshTokenRepository.findByCustomerId(customerId).ifPresent(refreshTokenRepository::delete);
    }

    private RefreshToken refreshExistingRefreshToken(RefreshToken refreshToken) {
        logger.info("Refreshing existing refresh token");

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));

        return refreshToken;
    }

    private RefreshToken createNewRefreshToken(UUID customerId) {
        logger.info("Creating new refresh token");

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCustomer(customerValidation.validateEntityById(customerId));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));

        return refreshToken;
    }

    private void verifyExpiration(RefreshToken token) {
        logger.info("Verifying refresh accessToken...");
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            logger.error("Refresh accessToken expired");
            throw new TokenRefreshException(token.getToken());
        }
        logger.info("Refresh accessToken is valid");
    }
}
