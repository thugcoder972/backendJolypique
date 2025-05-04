package com.mysycorp.Backendjo.controller;

import com.mysycorp.Backendjo.dto.TicketDTO;
import com.mysycorp.Backendjo.entity.Ticket;
import com.mysycorp.Backendjo.entity.Tarif;
import com.mysycorp.Backendjo.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Ticket ticket = convertToEntity(ticketDTO);
        Ticket createdTicket = ticketService.createTicket(ticket);
        return ResponseEntity.ok(convertToDto(createdTicket));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> updateTicket(
            @PathVariable Long id, 
            @RequestBody TicketDTO ticketDTO) {
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
        dto.setIsUsed(ticket.getIsUsed());
        dto.setStartTimeEpreuve(ticket.getStartTimeEpreuve());
        dto.setRemainingPlaces(ticket.getRemainingPlaces());
        
        if (ticket.getAchat() != null) dto.setAchatId(ticket.getAchat().getId());
        if (ticket.getAdministration() != null) dto.setAdministrationId(ticket.getAdministration().getId());
        if (ticket.getComplexeSportif() != null) dto.setComplexeSportifId(ticket.getComplexeSportif().getId());
        if (ticket.getEpreuveSportive() != null) dto.setEpreuveSportiveId(ticket.getEpreuveSportive().getId());
        if (ticket.getHall() != null) dto.setHallId(ticket.getHall().getId());
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
        ticket.setIsUsed(dto.getIsUsed());
        ticket.setStartTimeEpreuve(dto.getStartTimeEpreuve());
        ticket.setRemainingPlaces(dto.getRemainingPlaces());
        
        // Note: Les relations seront gérées séparément via leurs services
        return ticket;
    }
}