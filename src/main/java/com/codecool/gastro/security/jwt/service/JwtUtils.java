package com.codecool.gastro.security.jwt.service;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${gastro.app.jwtSecret}")
    private String jwtSecret;

    @Value("${gastro.app.jwtTokenExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        String email;
        try {
            email = ((UserDetails) authentication.getPrincipal()).getUsername();
        } catch (ClassCastException ex) {
            email = ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttribute("email");
        }

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String generateTokenFromEmail(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key()).build()
                    .parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException ex) {
            return ex.getClaims().getSubject();
        }
    }

    public boolean hasTokenExpired(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return false;
        } catch (ExpiredJwtException ex) {
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("JWT token is expired: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("JWT token is unsupported: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty: {}", ex.getMessage());
        }
        return false;
    }

    public String parseJwt(HttpServletRequest request) {
        if (Objects.isNull(request.getCookies())) {
            return null;
        }

        Optional<Cookie> jwtToken = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("JWT_TOKEN"))
                .findFirst();

        return jwtToken.map(Cookie::getValue).orElse(null);
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

}
