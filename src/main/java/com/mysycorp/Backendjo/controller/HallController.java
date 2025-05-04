package com.mysycorp.Backendjo.controller;

import com.mysycorp.Backendjo.dto.HallDTO;
import com.mysycorp.Backendjo.entity.Hall;
import com.mysycorp.Backendjo.repository.HallRepository;
import com.mysycorp.Backendjo.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
public class HallController {

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private HallService hallService;

    // Endpoints originaux (tels quels)
    @GetMapping
    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hall> getHallById(@PathVariable Long id) {
        return hallRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Hall createHall(@RequestBody Hall hall) {
        return hallRepository.save(hall);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hall> updateHall(@PathVariable Long id, @RequestBody Hall hallDetails) {
        return hallRepository.findById(id)
                .map(hall -> {
                    hall.setName(hallDetails.getName());
                    hall.setNumberPlace(hallDetails.getNumberPlace());
                    hall.setComplexeSportif(hallDetails.getComplexeSportif());
                    Hall updatedHall = hallRepository.save(hall);
                    return ResponseEntity.ok(updatedHall);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteHall(@PathVariable Long id) {
        return hallRepository.findById(id)
                .map(hall -> {
                    hallRepository.delete(hall);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    // Nouveaux endpoints avec DTO
    @GetMapping("/dto")
    public List<HallDTO> getAllHallsDto() {
        return hallService.getAllHalls();
    }

    @GetMapping("/{id}/dto")
    public ResponseEntity<HallDTO> getHallDtoById(@PathVariable Long id) {
        return hallRepository.findById(id)
                .map(hallService::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/dto")
    public HallDTO createHallDto(@RequestBody HallDTO hallDTO) {
        Hall hall = hallService.convertToEntity(hallDTO);
        Hall savedHall = hallRepository.save(hall);
        return hallService.convertToDto(savedHall);
    }

    @PutMapping("/{id}/dto")
    public ResponseEntity<HallDTO> updateHallDto(@PathVariable Long id, @RequestBody HallDTO hallDTO) {
        return hallRepository.findById(id)
                .map(existingHall -> {
                    existingHall.setName(hallDTO.getName());
                    existingHall.setNumberPlace(hallDTO.getNumberPlace());
                    // Note: Vous devrez gérer complexeSportifId séparément
                    Hall updatedHall = hallRepository.save(existingHall);
                    return ResponseEntity.ok(hallService.convertToDto(updatedHall));
                }).orElse(ResponseEntity.notFound().build());
    }
}