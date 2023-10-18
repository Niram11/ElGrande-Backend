package com.codecool.gastro.security.oauth2;

import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.entity.CustomerRole;
import com.codecool.gastro.security.jwt.entity.OAuth2ClientToken;
import com.codecool.gastro.repository.entity.Role;
import com.codecool.gastro.security.jwt.JwtUtils;
import com.codecool.gastro.security.jwt.service.OAuth2ClientTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final OAuth2ClientTokenService oAuth2ClientTokenService;
    private final CustomerRepository customerRepository;
    private final JwtUtils jwtUtils;

    @Value("${gastro.app.frontendUrl}")
    private String frontendUrl;

    public OAuth2LoginSuccessHandler(OAuth2ClientTokenService oAuth2ClientTokenService, CustomerRepository customerRepository, JwtUtils jwtUtils) {
        this.oAuth2ClientTokenService = oAuth2ClientTokenService;
        this.customerRepository = customerRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        if ("google".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = principal.getAttributes();
            String email = attributes.getOrDefault("email", "").toString();
            String name = attributes.getOrDefault("name", "").toString();
            customerRepository.findByEmail(email)
                    .ifPresentOrElse(customer ->
                                    authenticateUser(customer, attributes, oAuth2AuthenticationToken, request),
                            () -> {
                                Customer customer = createCustomer(email, name);
                                authenticateUser(customer, attributes, oAuth2AuthenticationToken, request);
                            });
        }

        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(frontendUrl);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private Customer createCustomer(String email, String name) {
        Role role = new Role();
        role.setRole(CustomerRole.ROLE_USER);

        Customer customer = new Customer();
        customer.assignRole(role);
        customer.setEmail(email);
        customer.setName(name);
        customer.setSubmissionTime(LocalDate.now());
        customerRepository.save(customer);
        return customer;
    }

    private void authenticateUser(Customer customer, Map<String, Object> attributes,
                                  OAuth2AuthenticationToken oAuth2AuthenticationToken, HttpServletRequest request) {
        List<SimpleGrantedAuthority> roles = customer.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name())).toList();

        DefaultOAuth2User newUser = new DefaultOAuth2User(roles, attributes, "name");

        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, roles,
                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());

        oAuth2ClientTokenService.createNewOAuth2ClientToken(customer, request.getSession().getId(),
                jwtUtils.generateJwtToken(oAuth2AuthenticationToken));

        SecurityContextHolder.getContext().setAuthentication(securityAuth);
    }
}
