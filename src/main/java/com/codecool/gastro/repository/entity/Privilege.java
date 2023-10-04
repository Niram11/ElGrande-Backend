//package com.codecool.gastro.repository.entity;
//
//import jakarta.persistence.*;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.UUID;
//
//@Entity
//public class Privilege {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;
//    @Enumerated(EnumType.STRING)
//    private PrivilegeEnum name;
//    @ManyToMany
//    private Collection<Role> roles;
//
//    public Privilege() {
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
//    public PrivilegeEnum getName() {
//        return name;
//    }
//
//    public void setName(PrivilegeEnum name) {
//        this.name = name;
//    }
//
//    public Collection<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Collection<Role> roles) {
//        this.roles = roles;
//    }
//}
