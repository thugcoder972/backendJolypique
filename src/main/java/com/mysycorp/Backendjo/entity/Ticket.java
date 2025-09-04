package com.mysycorp.Backendjo.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import tech.ailef.snapadmin.external.annotations.DisplayName;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String seat;

    @Column(nullable = false)
    private Boolean isUsed = false;

    @Column(nullable = false)
    private Double price; // Champ ajouté pour résoudre l'erreur NOT NULL

    @Column(nullable = false)
    private LocalDateTime startTimeEpreuve;

    @Column(nullable = false)
    private Integer remainingPlaces;

    @ManyToOne
    @JoinColumn(name = "achat_id")
    @JsonBackReference
    private Achat achat;

    @ManyToOne
    @JoinColumn(name = "administration_id", nullable = true)
    private Administration administration;

    @ManyToOne
    @JoinColumn(name = "complexe_sportif_id", nullable = false)
    private ComplexeSportif complexeSportif;

    @ManyToOne
    @JoinColumn(name = "epreuve_sportive_id", nullable = false)
    private EpreuveSportive epreuveSportive;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @ManyToMany
    @JoinTable(
        name = "ticket_tarif",
        joinColumns = @JoinColumn(name = "ticket_id"),
        inverseJoinColumns = @JoinColumn(name = "tarif_id")
    )
    private Set<Tarif> tarifs = new HashSet<>();

    public Ticket() {
        // Constructeur par défaut nécessaire pour JPA
    }

    @JsonCreator
    public Ticket(
        @JsonProperty("seat") String seat,
        @JsonProperty("isUsed") Boolean isUsed,
        @JsonProperty("price") Double price,
        @JsonProperty("startTimeEpreuve") LocalDateTime startTimeEpreuve,
        @JsonProperty("remainingPlaces") Integer remainingPlaces) {
        
        this.seat = seat;
        this.isUsed = isUsed != null ? isUsed : false;
        this.price = price; // Initialisation du nouveau champ
        this.startTimeEpreuve = startTimeEpreuve;
        this.remainingPlaces = remainingPlaces;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed != null ? isUsed : false;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getStartTimeEpreuve() {
        return startTimeEpreuve;
    }

    public void setStartTimeEpreuve(LocalDateTime startTimeEpreuve) {
        this.startTimeEpreuve = startTimeEpreuve;
    }

    public Integer getRemainingPlaces() {
        return remainingPlaces;
    }

    public void setRemainingPlaces(Integer remainingPlaces) {
        this.remainingPlaces = remainingPlaces;
    }

    public Achat getAchat() {
        return achat;
    }

    public void setAchat(Achat achat) {
        this.achat = achat;
    }

    public Administration getAdministration() {
        return administration;
    }

    public void setAdministration(Administration administration) {
        this.administration = administration;
    }

    @DisplayName
    public ComplexeSportif getComplexeSportif() {
        return complexeSportif;
    }

    public void setComplexeSportif(ComplexeSportif complexeSportif) {
        this.complexeSportif = complexeSportif;
    }

    @DisplayName
    public EpreuveSportive getEpreuveSportive() {
        return epreuveSportive;
    }

    public void setEpreuveSportive(EpreuveSportive epreuveSportive) {
        this.epreuveSportive = epreuveSportive;
    }

    @DisplayName
    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Set<Tarif> getTarifs() {
        return tarifs;
    }

    public void setTarifs(Set<Tarif> tarifs) {
        this.tarifs = tarifs;
    }

    public boolean isUsed() {
        return Boolean.TRUE.equals(isUsed);
    }

    public void setUsed(boolean used) {
        this.isUsed = used;
    }
}