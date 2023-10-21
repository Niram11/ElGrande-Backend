package com.codecool.gastro.security.jwt.service;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.security.jwt.dto.JwtResponse;
import com.codecool.gastro.security.jwt.entity.OAuth2ClientToken;
import com.codecool.gastro.security.jwt.entity.RefreshToken;
import com.codecool.gastro.security.jwt.repository.OAuth2ClientTokenRepository;
import com.codecool.gastro.security.jwt.service.exception.SessionNotRegisteredException;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OAuth2ClientTokenService {
    private final OAuth2ClientTokenRepository oAuth2ClientTokenRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final RefreshTokenService refreshTokenService;

    public OAuth2ClientTokenService(OAuth2ClientTokenRepository oAuth2ClientTokenRepository, CustomerRepository customerRepository, CustomerMapper customerMapper, RefreshTokenService refreshTokenService) {
        this.oAuth2ClientTokenRepository = oAuth2ClientTokenRepository;
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.refreshTokenService = refreshTokenService;
    }

    public JwtResponse getTokenByJSessionId(String jSessionId) {
        return oAuth2ClientTokenRepository.findByJSessionId(jSessionId)
                .map(token -> customerRepository.findById(token.getCustomer().getId())
                        .map(customer -> {
                            CustomerDto customerDto = customerMapper.toDto(customer);
                            RefreshToken refreshToken = refreshTokenService.createRefreshToken(customer.getId());
                            return new JwtResponse(
                                    token.getJwtToken(),
                                    "Bearer",
                                    refreshToken.getToken(),
                                    customer.getId(),
                                    customer.getEmail(),
                                    customerDto.roles()
                            );
                        }).orElseThrow(() -> new ObjectNotFoundException(token.getCustomer().getId(), Customer.class))
                ).orElseThrow(() -> new SessionNotRegisteredException(jSessionId));

    }

    public void createNewOAuth2ClientToken(Customer customer, String jSessionId, String jwtToken) {
        oAuth2ClientTokenRepository.findByCustomerId(customer.getId())
                .ifPresentOrElse(s -> {
                        },
                        () -> {
                            OAuth2ClientToken oAuth2ClientToken = new OAuth2ClientToken();
                            oAuth2ClientToken.setCustomer(customer);
                            oAuth2ClientToken.setJSessionId(jSessionId);
                            oAuth2ClientToken.setJwtToken(jwtToken);
                            oAuth2ClientTokenRepository.save(oAuth2ClientToken);
                        });

    }

    public void deleteByCustomerId(UUID customerId) {
        oAuth2ClientTokenRepository.findByCustomerId(customerId)
                .ifPresent(oAuth2ClientTokenRepository::delete);
    }
}
