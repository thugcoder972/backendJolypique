package com.mysycorp.Backendjo.controller;

import com.mysycorp.Backendjo.dto.EpreuveSportiveDTO;
import com.mysycorp.Backendjo.entity.EpreuveSportive;
import com.mysycorp.Backendjo.repository.EpreuveSportiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/epreuves-sportives")
public class EpreuveSportiveController {

    @Autowired
    private EpreuveSportiveRepository epreuveSportiveRepository;

    private EpreuveSportiveDTO convertToDto(EpreuveSportive epreuve) {
        EpreuveSportiveDTO dto = new EpreuveSportiveDTO();
        dto.setId(epreuve.getId());
        dto.setNameEpreuveSportive(epreuve.getNameEpreuveSportive());
        dto.setTypeEpreuveSportive(epreuve.getTypeEpreuveSportive());
        dto.setNiveauEpreuve(epreuve.getNiveauEpreuve());
        dto.setImageUrl(epreuve.getImageUrl());
        dto.setHall(epreuve.getHall() != null ? epreuve.getHall().getName() : null);
        dto.setDurationInSeconds(epreuve.getDurationInSeconds());
        dto.setTicketPrice(epreuve.getTicketPrice());
        return dto;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpreuveSportiveDTO> getEpreuveById(@PathVariable Long id) {
        return epreuveSportiveRepository.findById(id)
            .map(this::convertToDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EpreuveSportiveDTO>> getAllEpreuves() {
        List<EpreuveSportiveDTO> dtos = epreuveSportiveRepository.findAll()
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<EpreuveSportiveDTO> createEpreuve(
            @Valid @RequestBody EpreuveSportive epreuve,
            BindingResult result) {
        
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        
        EpreuveSportive saved = epreuveSportiveRepository.save(epreuve);
        return ResponseEntity.ok(convertToDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EpreuveSportiveDTO> updateEpreuve(
            @PathVariable Long id,
            @Valid @RequestBody EpreuveSportive epreuveDetails,
            BindingResult result) {
        
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        
        return epreuveSportiveRepository.findById(id)
            .map(epreuve -> {
                epreuve.setNameEpreuveSportive(epreuveDetails.getNameEpreuveSportive());
                epreuve.setTypeEpreuveSportive(epreuveDetails.getTypeEpreuveSportive());
                epreuve.setNiveauEpreuve(epreuveDetails.getNiveauEpreuve());
                epreuve.setImageUrl(epreuveDetails.getImageUrl());
                epreuve.setHall(epreuveDetails.getHall());
                epreuve.setDurationInSeconds(epreuveDetails.getDurationInSeconds());
                epreuve.setTicketPrice(epreuveDetails.getTicketPrice());
                
                EpreuveSportive updated = epreuveSportiveRepository.save(epreuve);
                return ResponseEntity.ok(convertToDto(updated));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filter-by-type")
    public ResponseEntity<List<EpreuveSportiveDTO>> getEpreuvesByType(@RequestParam String type) {
        List<EpreuveSportiveDTO> dtos = epreuveSportiveRepository.findByTypeEpreuveSportive(type)
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
@GetMapping("/{id}/ticket-id")
public ResponseEntity<Long> getTicketIdByEpreuveId(@PathVariable Long id) {
    Optional<EpreuveSportive> epreuveOpt = epreuveSportiveRepository.findById(id);
    
    if (epreuveOpt.isEmpty()) {
        return ResponseEntity.notFound().build();
    }
    
    EpreuveSportive epreuve = epreuveOpt.get();
    Long ticketId = epreuve.getTicketId();
    
    if (ticketId == null) {
        return ResponseEntity.notFound().build();
    }
    
    return ResponseEntity.ok(ticketId);
}

}