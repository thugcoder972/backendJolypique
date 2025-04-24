package com.mysycorp.Backendjo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.mysycorp.Backendjo.dto.AchatDTO;
import com.mysycorp.Backendjo.entity.Achat;
import com.mysycorp.Backendjo.entity.Ticket;
import com.mysycorp.Backendjo.entity.User;
import com.mysycorp.Backendjo.exception.ResourceNotFoundException;
import com.mysycorp.Backendjo.mapper.AchatMapper;
import com.mysycorp.Backendjo.repository.AchatRepository;
import com.mysycorp.Backendjo.repository.TicketRepository;
import com.mysycorp.Backendjo.repository.UserRepository;

@RestController
@RequestMapping("/api/achats")
public class AchatController {

    @Autowired
    private AchatRepository achatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AchatMapper achatMapper;

    @PostMapping
    @Transactional
    public ResponseEntity<AchatDTO> createAchat(@RequestBody AchatDTO achatDTO) {
        // Solution simplifiée avec getReferenceById
        User user = userRepository.getReferenceById(achatDTO.getUser());
        
        // Version basique sans JOIN FETCH
        List<Ticket> tickets = achatDTO.getTickets().stream()
                .map(ticketDTO -> ticketRepository.findById(ticketDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        if (tickets.size() != achatDTO.getTickets().size()) {
            throw new ResourceNotFoundException("Un ou plusieurs tickets non trouvés");
        }

        Achat achat = new Achat();
        achat.setUser(user);
        achat.setDateAchat(achatDTO.getDateAchat());
        achat.setNombreTickets(tickets.size());
        
        // Calcul simplifié du prix (si nécessaire)
        double prixTotal = tickets.stream()
                .flatMap(t -> t.getTarifs().stream())
                .mapToDouble(t -> t.getTarif())
                .sum();
        achat.setPrixTotal(prixTotal);

        Achat savedAchat = achatRepository.save(achat);
        
        // Retourne uniquement les données essentielles
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(achatMapper.toDTO(savedAchat));
    }

    // Les autres méthodes restent inchangées
    @GetMapping
    public ResponseEntity<List<AchatDTO>> getAllAchats() {
        List<Achat> achats = achatRepository.findAll();
        return ResponseEntity.ok(
            achats.stream()
                .map(achatMapper::toDTO)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchatDTO> getAchatById(@PathVariable Long id) {
        return achatRepository.findById(id)
                .map(achat -> ResponseEntity.ok(achatMapper.toDTO(achat)))
                .orElse(ResponseEntity.notFound().build());
    }
}