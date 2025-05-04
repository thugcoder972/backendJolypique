package com.mysycorp.Backendjo.controller;

import com.mysycorp.Backendjo.entity.Administration;
import com.mysycorp.Backendjo.entity.User;
import com.mysycorp.Backendjo.repository.AdministrationRepository;
import com.mysycorp.Backendjo.repository.UserRepository;
import com.mysycorp.Backendjo.service.AdministrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrations")
public class AdministrationController {

    private final AdministrationService administrationService;
    private final UserRepository userRepository;

    public AdministrationController(AdministrationService administrationService, 
                                  UserRepository userRepository) {
        this.administrationService = administrationService;
        this.userRepository = userRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<Administration>> getAllAdministrations() {
        return ResponseEntity.ok(administrationService.getAllAdministrationsWithComplexes());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Administration> getAdministrationById(@PathVariable Long id) {
        return administrationService.getAdministrationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Administration> createAdministration(@RequestBody Administration administration) {
        User user = administration.getUser();
        if (user != null && userRepository.existsById(user.getId())) {
            return ResponseEntity.ok(administrationService.saveAdministration(administration));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Administration> updateAdministration(
            @PathVariable Long id,
            @RequestBody Administration administrationDetails) {
            
        return administrationService.updateAdministration(id, administrationDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteAdministration(@PathVariable Long id) {
        if (administrationService.deleteAdministration(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
