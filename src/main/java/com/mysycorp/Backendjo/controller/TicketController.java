package com.mysycorp.Backendjo.controller;

import com.mysycorp.Backendjo.dto.TicketDTO;
import com.mysycorp.Backendjo.entity.*;
import com.mysycorp.Backendjo.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<Ticket> tickets = ticketService.findAllTickets();
        List<TicketDTO> dtos = tickets.stream()
                                    .map(this::convertToDto)
                                    .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketService.getTicketById(id);
        return ticket.map(t -> ResponseEntity.ok(convertToDto(t)))
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        // Validation du prix
        if (ticketDTO.getPrice() == null || ticketDTO.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le prix doit être supérieur à 0");
        }

        Ticket ticket = convertToEntity(ticketDTO);
        Ticket createdTicket = ticketService.createTicket(ticket);
        return ResponseEntity.ok(convertToDto(createdTicket));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> updateTicket(
            @PathVariable Long id, 
            @RequestBody TicketDTO ticketDTO) {
        // Validation du prix
        if (ticketDTO.getPrice() == null || ticketDTO.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le prix doit être supérieur à 0");
        }

        Ticket ticket = convertToEntity(ticketDTO);
        Ticket updatedTicket = ticketService.updateTicket(id, ticket);
        return ResponseEntity.ok(convertToDto(updatedTicket));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/mark-used")
    public ResponseEntity<TicketDTO> markTicketAsUsed(@PathVariable Long id) {
        Ticket ticket = ticketService.markTicketAsUsed(id);
        return ResponseEntity.ok(convertToDto(ticket));
    }

    private TicketDTO convertToDto(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setSeat(ticket.getSeat());
        dto.setUsed(ticket.isUsed());
        dto.setPrice(ticket.getPrice());
        dto.setStartTimeEpreuve(ticket.getStartTimeEpreuve());
        dto.setRemainingPlaces(ticket.getRemainingPlaces());
        
        if (ticket.getAchat() != null) {
            dto.setAchatId(ticket.getAchat().getId());
        }
        if (ticket.getAdministration() != null) {
            dto.setAdministrationId(ticket.getAdministration().getId());
        }
        if (ticket.getComplexeSportif() != null) {
            dto.setComplexeSportifId(ticket.getComplexeSportif().getId());
        }
        if (ticket.getEpreuveSportive() != null) {
            dto.setEpreuveSportiveId(ticket.getEpreuveSportive().getId());
        }
        if (ticket.getHall() != null) {
            dto.setHallId(ticket.getHall().getId());
        }
        if (ticket.getTarifs() != null) {
            Set<Long> tarifIds = ticket.getTarifs().stream()
                                    .map(Tarif::getId)
                                    .collect(Collectors.toSet());
            dto.setTarifIds(tarifIds);
        }
        
        return dto;
    }

    private Ticket convertToEntity(TicketDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setSeat(dto.getSeat());
        ticket.setUsed(dto.isUsed());
        ticket.setPrice(dto.getPrice());
        ticket.setStartTimeEpreuve(dto.getStartTimeEpreuve());
        ticket.setRemainingPlaces(dto.getRemainingPlaces());
        
        // Gestion des relations via leurs IDs
        if (dto.getAchatId() != null) {
            Achat achat = new Achat();
            achat.setId(dto.getAchatId());
            ticket.setAchat(achat);
        }
        if (dto.getAdministrationId() != null) {
            Administration admin = new Administration();
            admin.setId(dto.getAdministrationId());
            ticket.setAdministration(admin);
        }
        if (dto.getComplexeSportifId() != null) {
            ComplexeSportif complexe = new ComplexeSportif();
            complexe.setId(dto.getComplexeSportifId());
            ticket.setComplexeSportif(complexe);
        }
        if (dto.getEpreuveSportiveId() != null) {
            EpreuveSportive epreuve = new EpreuveSportive();
            epreuve.setId(dto.getEpreuveSportiveId());
            ticket.setEpreuveSportive(epreuve);
        }
        if (dto.getHallId() != null) {
            Hall hall = new Hall();
            hall.setId(dto.getHallId());
            ticket.setHall(hall);
        }
        
        // Note: Les tarifs seront gérés séparément via le service
        return ticket;
    }
}