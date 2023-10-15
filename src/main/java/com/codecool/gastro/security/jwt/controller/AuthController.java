package com.codecool.gastro.security.jwt.controller;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.security.jwt.JwtUtils;
import com.codecool.gastro.security.jwt.dto.JwtResponse;
import com.codecool.gastro.security.jwt.dto.LoginRequest;
import com.codecool.gastro.security.jwt.dto.TokenRefreshRequest;
import com.codecool.gastro.security.jwt.dto.TokenRefreshResponse;
import com.codecool.gastro.security.jwt.entity.RefreshToken;
import com.codecool.gastro.security.jwt.service.RefreshTokenService;
import com.codecool.gastro.service.CustomerService;
import com.codecool.gastro.service.exception.TokenRefreshException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auths")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomerService customerService;
    private final JwtUtils jwtUtils;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager, CustomerService customerService,
                          JwtUtils jwtUtils, OAuth2AuthorizedClientService authorizedClientService,
                          RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.jwtUtils = jwtUtils;
        this.authorizedClientService = authorizedClientService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/jwt/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        CustomerDto customerDto = customerService.getCustomerByEmail(userDetails.getUsername());

        String jwt = jwtUtils.generateJwtToken(authentication);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(customerDto.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse(
                jwt,
                "Bearer",
                refreshToken.getToken(),
                customerDto.id(),
                customerDto.email(),
                customerDto.roles()
        ));
    }

    @PostMapping("/jwt/signup")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody NewCustomerDto newCustomerDto) {
        customerService.saveCustomer(newCustomerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/jwt/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshToken(
            @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {

        return refreshTokenService.findByToken(tokenRefreshRequest.token())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getCustomer)
                .map(customer -> {
                    String token = jwtUtils.generateTokenFromEmail(customer.getEmail());
                    refreshTokenService.deleteByCustomerId(customer.getId());
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(customer.getId());
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new TokenRefreshResponse(token, newRefreshToken.getToken(), "Bearer"));
                })
                .orElseThrow(() -> new TokenRefreshException(tokenRefreshRequest.token()));
    }

    @PostMapping("/jwt/logout")
    public ResponseEntity<Void> logoutCustomer() {
        try {
            Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            refreshTokenService.deleteByCustomerId(customer.getId());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
    }

    @GetMapping("/oauth2")
    public ResponseEntity<CustomerDto> getOAuth2User(@AuthenticationPrincipal OAuth2User user) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.getCustomerByEmail(user.getAttribute("email")));
    }

    @GetMapping("/oauth2/refresh")
    public ResponseEntity<?> refreshOAuth2Token(
            @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient user) {
        authorizedClientService.removeAuthorizedClient(user.getClientRegistration().getRegistrationId(),
                user.getPrincipalName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
