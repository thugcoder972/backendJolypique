package com.mysycorp.Backendjo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "achats")
public class Achat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int nombreTickets;
    private LocalDateTime dateAchat;
    private double prixTotal;  // Ajout de l'attribut prixTotal

    // Nouveaux champs pour le paiement
    private String paymentStatus; // "PENDING", "PAID", "FAILED"
    private String transactionId; // Identifiant unique du paiement
    private LocalDateTime paymentDate; // Quand le paiement a été fait

    // Relation ManyToOne avec Tarif : un achat est associé à un tarif
    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "tarif_id", nullable = false)
    // private Tarif tarif;

    // Relation ManyToOne avec User : un achat est fait par un utilisateur
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relation OneToMany avec Ticket : un achat peut avoir plusieurs tickets
    @OneToMany(mappedBy = "achat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    // Constructeurs
    public Achat() {
    }

    public Achat(int nombreTickets, LocalDateTime dateAchat, double prixTotal, Tarif tarif, User user) {
        this.nombreTickets = nombreTickets;
        this.dateAchat = dateAchat;
        this.prixTotal = prixTotal;
        //this.tarif = tarif;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNombreTickets() {
        return nombreTickets;
    }

    public void setNombreTickets(int nombreTickets) {
        this.nombreTickets = nombreTickets;
    }

    public LocalDateTime getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDateTime dateAchat) {
        this.dateAchat = dateAchat;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    // public Tarif getTarif() {
    //     return tarif;
    // }

    // public void setTarif(Tarif tarif) {
    //     this.tarif = tarif;
    // }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    // Méthodes utilitaires
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setAchat(this);
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
        ticket.setAchat(null);
    }

    // --- GETTERS payment---
    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public LocalDateTime getPaymentDate() {
        return this.paymentDate;
    }

    // --- SETTERS payment ---
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}
