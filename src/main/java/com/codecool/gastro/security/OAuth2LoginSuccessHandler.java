package com.codecool.gastro.security;

import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.entity.CustomerRole;
import com.codecool.gastro.service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    private CustomerRepository customerRepository;

    @Value("${gastro.app.frontendUrl}")
    private String frontendUrl;

    public OAuth2LoginSuccessHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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
                    .ifPresentOrElse(customer -> authenticateUser(customer, attributes, oAuth2AuthenticationToken),
                            () -> {
                                Customer customer = createCustomer(email, name);
                                authenticateUser(customer, attributes, oAuth2AuthenticationToken);
                            });
        }


        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(frontendUrl);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private Customer createCustomer(String email, String name) {
        Customer customer = new Customer();
        customer.setRole(CustomerRole.USER);
        customer.setEmail(email);
        customer.setName(name);
        customer.setSubmissionTime(LocalDate.now());
        customerRepository.save(customer);
        return customer;
    }

    private void authenticateUser(Customer customer, Map<String, Object> attributes, OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(customer.getRole().name())),
                attributes, "name");

        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(
                new SimpleGrantedAuthority(customer.getRole().name())
        ), oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());

        SecurityContextHolder.getContext().setAuthentication(securityAuth);
    }
}
