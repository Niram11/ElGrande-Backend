package com.codecool.gastro.security.jwt.controller;

import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.security.jwt.dto.JwtResponse;
import com.codecool.gastro.security.jwt.dto.LoginRequest;
import com.codecool.gastro.security.jwt.dto.TokenRefreshRequest;
import com.codecool.gastro.security.jwt.dto.TokenRefreshResponse;
import com.codecool.gastro.security.jwt.entity.RefreshToken;
import com.codecool.gastro.security.jwt.service.JwtUtils;
import com.codecool.gastro.security.jwt.service.OAuth2ClientTokenService;
import com.codecool.gastro.security.jwt.service.RefreshTokenService;
import com.codecool.gastro.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auths")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomerService customerService;
    private final JwtUtils jwtUtils;
    private final OAuth2ClientTokenService oAuth2ClientTokenService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager, CustomerService customerService,
                          JwtUtils jwtUtils,
                          OAuth2ClientTokenService oAuth2ClientTokenService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.jwtUtils = jwtUtils;
        this.oAuth2ClientTokenService = oAuth2ClientTokenService;
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

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new JwtResponse(
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
            @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest, HttpServletRequest request) {
        Optional<String> token = jwtUtils.parseJwt(request);
        if (token.isPresent() && jwtUtils.hasTokenExpired(token.get())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(refreshTokenService.handleRefreshTokenRequest(tokenRefreshRequest));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @PostMapping("/jwt/logout")
    public ResponseEntity<Void> logoutCustomer(HttpServletRequest request) {
        Optional<String> token = jwtUtils.parseJwt(request);
        if (token.isPresent()) {
            try {
                Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                refreshTokenService.deleteByCustomerId(customer.getId());
                oAuth2ClientTokenService.deleteByCustomerId(customer.getId());
            } catch (ClassCastException ex) {
                CustomerDto customerDto = customerService.getCustomerByEmail(jwtUtils.getEmailFromJwtToken(token.get()));
                refreshTokenService.deleteByCustomerId(customerDto.id());
                oAuth2ClientTokenService.deleteByCustomerId(customerDto.id());
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/oauth2")
    public ResponseEntity<JwtResponse> getOAuth2User(@CookieValue("JSESSIONID") String jSessionId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(oAuth2ClientTokenService.getTokenByJSessionId(jSessionId));
    }

}
