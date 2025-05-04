package com.mysycorp.Backendjo.controller;

import com.mysycorp.Backendjo.dto.TarifDTO;
import com.mysycorp.Backendjo.entity.Tarif;
import com.mysycorp.Backendjo.repository.TarifRepository;
import com.mysycorp.Backendjo.service.TarifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tarifs")
public class TarifController {

    @Autowired
    private TarifRepository tarifRepository;

    @Autowired
    private TarifService tarifService;

    // Version avec DTO
    @PostMapping
    public ResponseEntity<?> createTarif(@RequestBody TarifDTO tarifDTO) {
        try {
            // Validation minimale
            if (tarifDTO.getNameTarif() == null || tarifDTO.getTarif() == null) {
                return ResponseEntity.badRequest().body("nameTarif et tarif sont obligatoires");
            }

            Tarif tarif = tarifService.convertToEntity(tarifDTO);
            Tarif savedTarif = tarifRepository.save(tarif);
            return ResponseEntity.status(HttpStatus.CREATED).body(tarifService.convertToDto(savedTarif));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de la création: " + e.getMessage());
        }
    }

    // Récupérer tous les tarifs (version DTO)
    @GetMapping
    public ResponseEntity<List<TarifDTO>> getAllTarifs() {
        List<TarifDTO> tarifs = tarifRepository.findAll()
                .stream()
                .map(tarifService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tarifs);
    }

    // Récupérer un tarif par ID (version DTO)
    @GetMapping("/{id}")
    public ResponseEntity<TarifDTO> getTarifById(@PathVariable Long id) {
        return tarifRepository.findById(id)
                .map(tarifService::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Mettre à jour un tarif
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTarif(@PathVariable Long id, @RequestBody TarifDTO tarifDTO) {
        return tarifRepository.findById(id)
                .map(existingTarif -> {
                    // Mise à jour des champs
                    existingTarif.setNameTarif(tarifDTO.getNameTarif());
                    existingTarif.setOffreTarif(tarifDTO.getOffreTarif());
                    existingTarif.setTarif(tarifDTO.getTarif());
                    
                    Tarif updated = tarifRepository.save(existingTarif);
                    return ResponseEntity.ok(tarifService.convertToDto(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarif(@PathVariable Long id) {
        if (!tarifRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tarifRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}