package com.mysycorp.Backendjo.dto;

import com.mysycorp.Backendjo.entity.ComplexeSportif;
import java.util.Set;
import java.util.stream.Collectors;

public class ComplexeSportifDTO {
    private Long id;
    private String nameComplexe;
    private String adresseComplexe;
    private AdministrationMinimalDTO administration;
    private Set<Long> hallIds;

    // Getters et Setters pour la classe principale
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public AdministrationMinimalDTO getAdministration() {
        return administration;
    }

    public void setAdministration(AdministrationMinimalDTO administration) {
        this.administration = administration;
    }

    public Set<Long> getHallIds() {
        return hallIds;
    }

    public void setHallIds(Set<Long> hallIds) {
        this.hallIds = hallIds;
    }

    // Sous-classe AdministrationMinimalDTO
    public static class AdministrationMinimalDTO {
        private Long id;
        private String nameAdministration;

        // Getters et Setters pour AdministrationMinimalDTO
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
    }

    // Méthode de conversion
    public static ComplexeSportifDTO fromEntity(ComplexeSportif complexe) {
        ComplexeSportifDTO dto = new ComplexeSportifDTO();
        dto.setId(complexe.getId());
        dto.setNameComplexe(complexe.getNameComplexe());
        dto.setAdresseComplexe(complexe.getAdresseComplexe());

        if (complexe.getAdministration() != null) {
            AdministrationMinimalDTO adminDto = new AdministrationMinimalDTO();
            adminDto.setId(complexe.getAdministration().getId());
            adminDto.setNameAdministration(complexe.getAdministration().getNameAdministration());
            dto.setAdministration(adminDto);
        }

        if (complexe.getHalls() != null) {
            dto.setHallIds(complexe.getHalls().stream()
                .map(hall -> hall.getId())
                .collect(Collectors.toSet()));
        }

        return dto;
    }
}