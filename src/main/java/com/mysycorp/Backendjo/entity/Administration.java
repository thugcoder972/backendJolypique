package com.mysycorp.Backendjo.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Administration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nameAdministration;

    @Column(nullable = false, length = 255)
    private String adresseAdministration;

    @Column(length = 100)
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "administration", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ComplexeSportif> complexes = new HashSet<>();

    @OneToMany(mappedBy = "administration", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ticket> tickets = new HashSet<>();

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAdministration() {
        return nameAdministration;
    }

    public void setNameAdministration(String nameAdministration) {
        this.nameAdministration = nameAdministration;
    }

    public String getAdresseAdministration() {
        return adresseAdministration;
    }

    public void setAdresseAdministration(String adresseAdministration) {
        this.adresseAdministration = adresseAdministration;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ComplexeSportif> getComplexes() {
        return complexes;
    }

    public void setComplexes(Set<ComplexeSportif> complexes) {
        this.complexes = complexes;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}