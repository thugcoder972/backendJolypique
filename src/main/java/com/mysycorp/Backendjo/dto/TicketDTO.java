package com.mysycorp.Backendjo.dto;
import java.time.LocalDateTime;
import java.util.Set;

public class TicketDTO {
    private Long id;
    private LocalDateTime startTimeEpreuve;
    private Long achatId;
    private Long administrationId;
    private Long complexeSportifId;
    private Long epreuveSportiveId;
    private Long hallId;
    private Set<Long> tarifIds;
    private int remainingPlaces;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTimeEpreuve() {
        return startTimeEpreuve;
    }

    public void setStartTimeEpreuve(LocalDateTime startTimeEpreuve) {
        this.startTimeEpreuve = startTimeEpreuve;
    }

    public Long getAchatId() {
        return achatId;
    }

    public void setAchatId(Long achatId) {
        this.achatId = achatId;
    }

    public Long getAdministrationId() {
        return administrationId;
    }

    public void setAdministrationId(Long administrationId) {
        this.administrationId = administrationId;
    }

    public Long getComplexeSportifId() {
        return complexeSportifId;
    }

    public void setComplexeSportifId(Long complexeSportifId) {
        this.complexeSportifId = complexeSportifId;
    }

    public Long getEpreuveSportiveId() {
        return epreuveSportiveId;
    }

    public void setEpreuveSportiveId(Long epreuveSportiveId) {
        this.epreuveSportiveId = epreuveSportiveId;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public Set<Long> getTarifIds() {
        return tarifIds;
    }

    public void setTarifIds(Set<Long> tarifIds) {
        this.tarifIds = tarifIds;
    }

    public int getRemainingPlaces() {
        return remainingPlaces;
    }

    public void setRemainingPlaces(int remainingPlaces) {
        this.remainingPlaces = remainingPlaces;
    }
}
