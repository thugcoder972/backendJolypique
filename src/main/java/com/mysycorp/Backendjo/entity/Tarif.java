package com.mysycorp.Backendjo.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Tarif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nameTarif;

    @Column(nullable = false)
    private Double tarif;

    @Column(nullable = false, length = 255)
    private String offreTarif;

    // Relation ManyToMany avec Ticket : un tarif peut être associé à plusieurs tickets
    @ManyToMany(mappedBy = "tarifs")
    private Set<Ticket> tickets = new HashSet<>();

    // relation ManyToOne avec epreuve sportive : un tarif peu etre associé à plusieurs épreuves sportives
    @ManyToOne
    @JoinColumn(name = "epreuve_sportive_id", nullable = false)
    private EpreuveSportive epreuveSportive;
    
    // Relation OneToMany avec Achat : un tarif peut être associé à plusieurs achats
    //@OneToMany(mappedBy = "tarif", cascade = CascadeType.ALL)
    //private Set<Achat> achats = new HashSet<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameTarif() {
        return nameTarif;
    }

    public void setNameTarif(String nameTarif) {
        this.nameTarif = nameTarif;
    }

    public Double getTarif() {
        return tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }

    public String getOffreTarif() {
        return offreTarif;
    }

    public void setOffreTarif(String offreTarif) {
        this.offreTarif = offreTarif;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

   // public Set<Achat> getAchats() {
        //return achats;
    //}

    //public void setAchats(Set<Achat> achats) {
       // this.achats = achats;
    //}

    public EpreuveSportive getEpreuveSportive() {
        return epreuveSportive;
    }

    public void setEpreuveSportive(EpreuveSportive epreuveSportive) {
        this.epreuveSportive = epreuveSportive;
    }
}
