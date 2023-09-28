package com.codecool.gastro.controller;

import com.codecool.gastro.dto.auth.JwtResponse;
import com.codecool.gastro.dto.auth.LoginRequest;
import com.codecool.gastro.dto.auth.RefreshedTokenResponse;
import com.codecool.gastro.dto.auth.TokenRefreshRequest;
import com.codecool.gastro.dto.customer.NewCustomerDto;
import com.codecool.gastro.security.jwt.JwtUtils;
import com.codecool.gastro.security.service.UserDetailsImpl;
import com.codecool.gastro.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public AuthController(AuthenticationManager authenticationManager, CustomerService customerService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                "Bearer",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody NewCustomerDto newCustomerDto) {
        customerService.saveCustomer(newCustomerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshedTokenResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        String email = jwtUtils.getEmailFromJwtToken(tokenRefreshRequest.token());
        String jwt = jwtUtils.generateTokenFromEmail(email);
        return ResponseEntity.ok(new RefreshedTokenResponse(
                jwt,
                "Bearer"
        ));
    }

    @PostMapping("/oauth2/signup")
    public ResponseEntity<?> registerOAuth2User(@AuthenticationPrincipal OAuth2User user) {
        return ResponseEntity.ok("");
    }
}
