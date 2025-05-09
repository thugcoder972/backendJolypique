package com.mysycorp.Backendjo.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class TicketDTO {
    private Long id;
    private String seat;
    private boolean isUsed;
    private Double price;
    private LocalDateTime startTimeEpreuve;
    private Integer remainingPlaces;
    private Long achatId;
    private Long administrationId;
    private Long complexeSportifId;
    private Long epreuveSportiveId;
    private Long hallId;
    private Set<Long> tarifIds;

    // Getters
    public Long getId() {
        return id;
    }

    public String getSeat() {
        return seat;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDateTime getStartTimeEpreuve() {
        return startTimeEpreuve;
    }

    public Integer getRemainingPlaces() {
        return remainingPlaces;
    }

    public Long getAchatId() {
        return achatId;
    }

    public Long getAdministrationId() {
        return administrationId;
    }

    public Long getComplexeSportifId() {
        return complexeSportifId;
    }

    public Long getEpreuveSportiveId() {
        return epreuveSportiveId;
    }

    public Long getHallId() {
        return hallId;
    }

    public Set<Long> getTarifIds() {
        return tarifIds;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStartTimeEpreuve(LocalDateTime startTimeEpreuve) {
        this.startTimeEpreuve = startTimeEpreuve;
    }

    public void setRemainingPlaces(Integer remainingPlaces) {
        this.remainingPlaces = remainingPlaces;
    }

    public void setAchatId(Long achatId) {
        this.achatId = achatId;
    }

    public void setAdministrationId(Long administrationId) {
        this.administrationId = administrationId;
    }

    public void setComplexeSportifId(Long complexeSportifId) {
        this.complexeSportifId = complexeSportifId;
    }

    public void setEpreuveSportiveId(Long epreuveSportiveId) {
        this.epreuveSportiveId = epreuveSportiveId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public void setTarifIds(Set<Long> tarifIds) {
        this.tarifIds = tarifIds;
    }
}