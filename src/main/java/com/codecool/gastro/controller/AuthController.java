package com.codecool.gastro.controller;

import com.codecool.gastro.dto.auth.JwtResponse;
import com.codecool.gastro.dto.auth.LoginRequest;
import com.codecool.gastro.dto.auth.RefreshedTokenResponse;
import com.codecool.gastro.dto.auth.TokenRefreshRequest;
import com.codecool.gastro.dto.customer.CustomerDto;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.security.jwt.JwtUtils;
import com.codecool.gastro.security.service.UserDetailsImpl;
import com.codecool.gastro.service.CustomerService;
import com.nimbusds.oauth2.sdk.TokenRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auths")
public class AuthController {
    AuthenticationManager authenticationManager;
    CustomerService customerService;
    JwtUtils jwtUtils;
    OAuth2AuthorizedClientService authorizedClientService;

    public AuthController(AuthenticationManager authenticationManager, CustomerService customerService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/jwt")
    public ResponseEntity<CustomerDto> getJwtUser(@Valid @RequestBody TokenRefreshRequest token) {
        String email = jwtUtils.getEmailFromJwtToken(token.token());
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerByEmail(email));
    }

    @PostMapping("/jwt/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()
        ));
    }

    @GetMapping("/jwt/signup")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody NewCustomerDto newCustomerDto) {
        customerService.saveCustomer(newCustomerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/jwt/refresh-token")
    public ResponseEntity<RefreshedTokenResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        String email = jwtUtils.getEmailFromJwtToken(tokenRefreshRequest.token());
        String jwt = jwtUtils.generateTokenFromEmail(email);
        return ResponseEntity.ok(new RefreshedTokenResponse(
                jwt,
                "Bearer"
        ));
    }

    @GetMapping("/oauth2")
    public ResponseEntity<CustomerDto> getOAuth2User(@AuthenticationPrincipal OAuth2User user) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerByEmail(user.getAttribute("email")));
    }

    @GetMapping("/oauth2/refresh")
    public ResponseEntity<?> refreshOAuth2Token(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient user) {
        authorizedClientService.removeAuthorizedClient(user.getClientRegistration().getRegistrationId(), user.getPrincipalName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
