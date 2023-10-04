//package com.codecool.gastro.repository.entity;
//
//import jakarta.persistence.*;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.UUID;
//
//@Entity
//public class Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;
//    @Enumerated(EnumType.STRING)
//    private RoleEnum name;
//    @ManyToMany(mappedBy = "roles")
//    private List<Customer> customers;
//    @ManyToMany
//    @JoinTable(name = "role_privileges",
//            joinColumns = @JoinColumn(name = "role_id"),
//            inverseJoinColumns = @JoinColumn(name = "privilege_id"))
//    private Collection<Privilege> privileges;
//
//    public Role() {
//    }
//
//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }
//
//    public RoleEnum getName() {
//        return name;
//    }
//
//    public void setName(RoleEnum name) {
//        this.name = name;
//    }
//
//    public List<Customer> getCustomers() {
//        return customers;
//    }
//
//    public void setCustomers(List<Customer> customers) {
//        this.customers = customers;
//    }
//
//    public Collection<Privilege> getPrivileges() {
//        return privileges;
//    }
//
//    public void setPrivileges(Collection<Privilege> privileges) {
//        this.privileges = privileges;
//    }
//}
