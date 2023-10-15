package com.codecool.gastro.security;

import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.security.jwt.JwtUtils;
import com.codecool.gastro.service.exception.EmailNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomerRepository customerRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String jwt = jwtUtils.parseJwt(request);
            if (Objects.nonNull(jwt) && jwtUtils.validateJwtToken(jwt)) {

                logger.info("JwtToken {} has been successfully validated", jwt);
                String email = jwtUtils.getEmailFromJwtToken(jwt);

                Customer customer = customerRepository.findByEmail(email)
                        .orElseThrow(() -> new EmailNotFoundException(email));

                List<SimpleGrantedAuthority> authorities = customer.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRole().name())).toList();

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        customer,
                        null,
                        authorities
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        } catch (Exception ex) {
            logger.error("Cannot set user authentication: {}", ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
