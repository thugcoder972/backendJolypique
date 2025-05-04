package com.mysycorp.Backendjo.service;

import com.mysycorp.Backendjo.dto.HallDTO;
import com.mysycorp.Backendjo.entity.Hall;
import com.mysycorp.Backendjo.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HallService {

    @Autowired
    private HallRepository hallRepository;

    // Conversion Entity -> DTO
    public HallDTO convertToDto(Hall hall) {
        HallDTO dto = new HallDTO();
        dto.setId(hall.getId());
        dto.setName(hall.getName());
        dto.setNumberPlace(hall.getNumberPlace());
        if(hall.getComplexeSportif() != null) {
            dto.setComplexeSportifId(hall.getComplexeSportif().getId());
        }
        return dto;
    }

    // Conversion DTO -> Entity
    public Hall convertToEntity(HallDTO dto) {
        Hall hall = new Hall();
        hall.setId(dto.getId());
        hall.setName(dto.getName());
        hall.setNumberPlace(dto.getNumberPlace());
        // Note: Vous devrez charger le ComplexeSportif depuis son ID si nécessaire
        return hall;
    }

    public List<HallDTO> getAllHalls() {
        return hallRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}