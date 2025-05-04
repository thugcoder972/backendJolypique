package com.mysycorp.Backendjo.service;

import com.mysycorp.Backendjo.entity.Ticket;
import com.mysycorp.Backendjo.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional(readOnly = true)
    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    @Transactional
    public Ticket createTicket(Ticket ticket) {
        if (ticket.getIsUsed() == null) {
            ticket.setIsUsed(false);
        }
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket updateTicket(Long id, Ticket ticketDetails) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé"));
        
        if (ticketDetails.getSeat() != null) {
            ticket.setSeat(ticketDetails.getSeat());
        }
        if (ticketDetails.getStartTimeEpreuve() != null) {
            ticket.setStartTimeEpreuve(ticketDetails.getStartTimeEpreuve());
        }
        if (ticketDetails.getIsUsed() != null) {
            ticket.setIsUsed(ticketDetails.getIsUsed());
        }
        if (ticketDetails.getRemainingPlaces() != 0) {
            ticket.setRemainingPlaces(ticketDetails.getRemainingPlaces());
        }

        return ticketRepository.save(ticket);
    }

    @Transactional
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    @Transactional
    public Ticket markTicketAsUsed(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé"));
        ticket.setIsUsed(true);
        return ticketRepository.save(ticket);
    }
}