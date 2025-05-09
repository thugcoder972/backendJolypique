package com.mysycorp.Backendjo; // Correction du package

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysycorp.Backendjo.controller.TicketController;
import com.mysycorp.Backendjo.dto.TicketDTO;
import com.mysycorp.Backendjo.entity.*;
import com.mysycorp.Backendjo.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    // ... autres méthodes de test inchangées ...

    private Ticket createSampleTicket(Long id) {
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setSeat("A12");
        ticket.setUsed(false);
        ticket.setPrice(29.99);
        ticket.setStartTimeEpreuve(LocalDateTime.now());
        ticket.setRemainingPlaces(50);
        
        // Initialisation des relations avec des objets simples
        Achat achat = new Achat();
        achat.setId(1L);
        ticket.setAchat(achat);
        
        Administration admin = new Administration();
        admin.setId(1L);
        ticket.setAdministration(admin);
        
        ComplexeSportif complexe = new ComplexeSportif();
        complexe.setId(1L);
        ticket.setComplexeSportif(complexe);
        
        EpreuveSportive epreuve = new EpreuveSportive();
        epreuve.setId(1L);
        ticket.setEpreuveSportive(epreuve);
        
        Hall hall = new Hall();
        hall.setId(1L);
        ticket.setHall(hall);
        
        Tarif tarif = new Tarif();
        tarif.setId(1L);
        ticket.setTarifs(new HashSet<>(Arrays.asList(tarif)));
        
        return ticket;
    }

    private TicketDTO createSampleTicketDTO(Long id) {
        TicketDTO dto = new TicketDTO();
        dto.setId(id);
        dto.setSeat("A12");
        dto.setUsed(false);
        dto.setPrice(29.99);
        dto.setStartTimeEpreuve(LocalDateTime.now());
        dto.setRemainingPlaces(50);
        dto.setAchatId(1L);
        dto.setAdministrationId(1L);
        dto.setComplexeSportifId(1L);
        dto.setEpreuveSportiveId(1L);
        dto.setHallId(1L);
        dto.setTarifIds(new HashSet<>(Arrays.asList(1L)));
        return dto;
    }
}