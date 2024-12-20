package com.mysycorp.Backendjo.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private Long id; // Identifiant unique du ticket

    @Column(nullable = false)
    private String seat; // Numéro ou identifiant du siège

    @Column(nullable = false)
    private Boolean isUsed; // Indique si le ticket a été utilisé

    @Column(nullable = false)
    private LocalDateTime startTimeEpreuve; // Heure de début de l'épreuve associée au ticket

    // Relation ManyToOne avec Achat : un ticket est associé à un achat
    @ManyToOne
    @JoinColumn(name = "achat_id")

    private Achat achat;

    // Relation ManyToOne avec Administration : un ticket est géré par une administration
    @ManyToOne
    @JoinColumn(name = "administration_id", nullable = true)
    private Administration administration;

    // Relation ManyToOne avec ComplexeSportif : un ticket est associé à un complexe sportif
    @ManyToOne
    @JoinColumn(name = "complexe_sportif_id", nullable = false)
    private ComplexeSportif complexeSportif;

    // Relation ManyToOne avec EpreuveSportive : un ticket est associé à une épreuve sportive
    @ManyToOne
    @JoinColumn(name = "epreuve_sportive_id", nullable = false)
    private EpreuveSportive epreuveSportive;

    // Relation ManyToOne avec Hall : un ticket est lié à un hall
    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    // Relation ManyToMany avec Tarif : un ticket peut avoir plusieurs tarifs
    @ManyToMany
    @JoinTable(
        name = "ticket_tarif",
        joinColumns = @JoinColumn(name = "ticket_id"),
        inverseJoinColumns = @JoinColumn(name = "tarif_id")
    )
    private Set<Tarif> tarifs = new HashSet<>(); // Ensemble de tarifs associés au ticket

    @Column(nullable = false)
    private int remainingPlaces; // Nombre de places restantes pour cet événement

    // Constructeur par défaut
    public Ticket() {
    }

    // Constructeur pour la désérialisation JSON
    @JsonCreator
    public Ticket(
        @JsonProperty("seat") String seat,
        @JsonProperty("isUsed") Boolean isUsed,
        @JsonProperty("startTimeEpreuve") LocalDateTime startTimeEpreuve,
        @JsonProperty("remainingPlaces") int remainingPlaces) {
        
        this.seat = seat; // Initialise le siège
        this.isUsed = isUsed; // Initialise l'état d'utilisation
        this.startTimeEpreuve = startTimeEpreuve; // Initialise l'heure de début de l'épreuve
        this.remainingPlaces = remainingPlaces; // Initialise le nombre de places restantes
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
        this.isUsed = isUsed;
    }

    public LocalDateTime getStartTimeEpreuve() {
        return startTimeEpreuve;
    }

    public void setStartTimeEpreuve(LocalDateTime startTimeEpreuve) {
        this.startTimeEpreuve = startTimeEpreuve;
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

    public int getRemainingPlaces() {
        return remainingPlaces;
    }

    public void setRemainingPlaces(int remainingPlaces) {
        this.remainingPlaces = remainingPlaces;
    }

    // Méthode pour vérifier si le ticket est utilisé
    public boolean isUsed() {
        return Boolean.TRUE.equals(isUsed);
    }

    public void setUsed(Object used) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUsed'");
    }
}
