package com.mysycorp.Backendjo.dto;

public class HallDTO {
    private Long id;
    private String name;
    private int numberPlace;
    private Long complexeSportifId; // Juste l'ID pour éviter les références circulaires

    // Constructeurs
    public HallDTO() {}

    public HallDTO(Long id, String name, int numberPlace, Long complexeSportifId) {
        this.id = id;
        this.name = name;
        this.numberPlace = numberPlace;
        this.complexeSportifId = complexeSportifId;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberPlace() {
        return numberPlace;
    }

    public void setNumberPlace(int numberPlace) {
        this.numberPlace = numberPlace;
    }

    public Long getComplexeSportifId() {
        return complexeSportifId;
    }

    public void setComplexeSportifId(Long complexeSportifId) {
        this.complexeSportifId = complexeSportifId;
    }
}
