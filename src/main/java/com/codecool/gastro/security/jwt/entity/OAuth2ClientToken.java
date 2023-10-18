package com.codecool.gastro.security.jwt.entity;

import com.codecool.gastro.repository.entity.Customer;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class OAuth2ClientToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String jSessionId;
    private String jwtToken;
    @OneToOne
    private Customer customer;

    public OAuth2ClientToken() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getJSessionId() {
        return jSessionId;
    }

    public void setJSessionId(String JSessionId) {
        this.jSessionId = JSessionId;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
