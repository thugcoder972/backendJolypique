package com.mysycorp.Backendjo.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import tech.ailef.snapadmin.external.annotations.DisplayName;

@Entity
public class ComplexeSportif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nameComplexe;

    @Column(nullable = false, length = 255)
    private String adresseComplexe;

    // Relation ManyToOne avec Administration : un complexe sportif est géré par une administration
    @ManyToOne
    @JoinColumn(name = "administration_id", nullable = false)
    private Administration administration;

    // Relation OneToMany avec Hall : un complexe sportif peut avoir plusieurs halls    
    @OneToMany(mappedBy = "complexeSportif", cascade = CascadeType.ALL)
    private Set<Hall> halls = new HashSet<>();
    
    // Relation OneToMany avec Ticket : un complexe sportif peut avoir plusieurs tickets
    @OneToMany(mappedBy = "complexeSportif", cascade = CascadeType.ALL)
    private Set<Ticket> tickets = new HashSet<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @DisplayName
    public String getNameComplexe() {
        return nameComplexe;
    }

    public void setNameComplexe(String nameComplexe) {
        this.nameComplexe = nameComplexe;
    }

    public String getAdresseComplexe() {
        return adresseComplexe;
    }

    public void setAdresseComplexe(String adresseComplexe) {
        this.adresseComplexe = adresseComplexe;
    }

    public Administration getAdministration() {
        return administration;
    }

    public void setAdministration(Administration administration) {
        this.administration = administration;
    }

    public Set<Hall> getHalls() {
        return halls;
    }

    public void setHalls(Set<Hall> halls) {
        this.halls = halls;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getName() {
        return this.nameComplexe;
    }
    
    public void setName(String name) {
        this.nameComplexe = name;
    }
    
    public String getLocation() {
        return this.adresseComplexe;
    }
    
    public void setLocation(String location) {
        this.adresseComplexe = location;
    }

    
}


