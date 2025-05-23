package com.mysycorp.Backendjo.entity;

import java.time.Duration;
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
import jakarta.validation.constraints.NotNull;
import tech.ailef.snapadmin.external.annotations.DisplayName;

@Entity
public class EpreuveSportive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    @NotNull(message = "Le nom de l'épreuve sportive ne doit pas être null")
    private String nameEpreuveSportive;

    @Column(nullable = false, length = 255)
    @NotNull(message = "Le type d'épreuve sportive ne doit pas être null")
    private String typeEpreuveSportive;

    // Stocker la durée en secondes
    @Column(nullable = false)
    //@NotNull(message = "La durée de l'épreuve sportive ne doit pas être null")
    private Long durationInSeconds;

    @Column(nullable = false, length = 255)
    @NotNull(message = "Le niveau de l'épreuve ne doit pas être null")
    private String niveauEpreuve;
    
    @Column(nullable=true)
    private Double ticketPrice;

    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(length = 500)
    private String imageUrl;

    // Relation ManyToOne avec Hall : une épreuve sportive se déroule dans un hall
    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    @NotNull(message = "Le hall associé ne doit pas être null")
    private Hall hall;
    
    // Relation OneToMany avec Ticket : une épreuve sportive peut avoir plusieurs tickets
    @OneToMany(mappedBy = "epreuveSportive", cascade = CascadeType.ALL)
    private Set<Ticket> tickets = new HashSet<>();

    //relation OneToMany avec Tarif : une épreuve sportive peut avoir plusieurs tarifs
    @OneToMany(mappedBy = "epreuveSportive", cascade = CascadeType.ALL)
    private Set<Tarif> tarifs = new HashSet<>(); // Liste de tarifs pour une épreuve sportive
   
   
   
    // Getters and Setters
    public Long getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(Long durationInSeconds) {
        if (durationInSeconds == null || durationInSeconds <= 0) {
            throw new IllegalArgumentException("La durée ne peut pas être null ou inférieure à 0.");
        }
        this.durationInSeconds = durationInSeconds;
    }

    public Duration getDurationEpreuveSportive() {
        return Duration.ofSeconds(durationInSeconds);
    }

    public void setDurationEpreuveSportive(Duration durationEpreuveSportive) {
        this.durationInSeconds = durationEpreuveSportive.getSeconds();
    }
    @DisplayName
    public String getNameEpreuveSportive() {
        return nameEpreuveSportive;
    }

    public void setNameEpreuveSportive(String nameEpreuveSportive) {
        this.nameEpreuveSportive = nameEpreuveSportive;
    }

    public String getTypeEpreuveSportive() {
        return typeEpreuveSportive;
    }

    public void setTypeEpreuveSportive(String typeEpreuveSportive) {
        this.typeEpreuveSportive = typeEpreuveSportive;
    }

    public String getNiveauEpreuve() {
        return niveauEpreuve;
    }

    public void setNiveauEpreuve(String niveauEpreuve) {
        this.niveauEpreuve = niveauEpreuve;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Tarif> getTarifs() {
        return tarifs;
    }

    public void setTarifs(Set<Tarif> tarifs) {
        this.tarifs = tarifs;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

}
