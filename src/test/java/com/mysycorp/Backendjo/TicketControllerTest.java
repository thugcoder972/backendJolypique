package com.mysycorp.Backendjo;

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

    @Test
    void getAllTickets_ShouldReturnAllTickets() throws Exception {
        // Arrange
        Ticket ticket1 = createSampleTicket(1L);
        Ticket ticket2 = createSampleTicket(2L);
        when(ticketService.findAllTickets()).thenReturn(Arrays.asList(ticket1, ticket2));

        // Act & Assert
        mockMvc.perform(get("/api/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void getTicketById_ShouldReturnTicket() throws Exception {
        // Arrange
        Ticket ticket = createSampleTicket(1L);
        when(ticketService.getTicketById(1L)).thenReturn(Optional.of(ticket));

        // Act & Assert
        mockMvc.perform(get("/api/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.seat").value("A1"));
    }

    @Test
    void getTicketById_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(ticketService.getTicketById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/tickets/99"))
                .andExpect(status().isNotFound());
    }

  
    @Test
    void deleteTicket_ShouldReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(ticketService).deleteTicket(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/tickets/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void markTicketAsUsed_ShouldReturnUpdatedTicket() throws Exception {
        // Arrange
        Ticket ticket = createSampleTicket(1L);
        ticket.setIsUsed(true);
        when(ticketService.markTicketAsUsed(1L)).thenReturn(ticket);

        // Act & Assert
        mockMvc.perform(patch("/api/tickets/1/mark-used"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.isUsed").value(true));
    }

    // Helper methods
    private Ticket createSampleTicket(Long id) {
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setSeat("A1");
        ticket.setIsUsed(false);
        ticket.setStartTimeEpreuve(LocalDateTime.now());
        ticket.setRemainingPlaces(100);
        
        // Set related entities (simplified)
        ticket.setAchat(new Achat());
        ticket.getAchat().setId(1L);
        ticket.setAdministration(new Administration());
        ticket.getAdministration().setId(1L);
        ticket.setComplexeSportif(new ComplexeSportif());
        ticket.getComplexeSportif().setId(1L);
        ticket.setEpreuveSportive(new EpreuveSportive());
        ticket.getEpreuveSportive().setId(1L);
        ticket.setHall(new Hall());
        ticket.getHall().setId(1L);
        
        Tarif tarif = new Tarif();
        tarif.setId(1L);
        ticket.setTarifs(Set.of(tarif));
        
        return ticket;
    }

    private TicketDTO createSampleTicketDTO(Long id) {
        TicketDTO dto = new TicketDTO();
        dto.setId(id);
        dto.setSeat("A1");
        dto.setIsUsed(false);
        dto.setStartTimeEpreuve(LocalDateTime.now());
        dto.setRemainingPlaces(100);
        dto.setAchatId(1L);
        dto.setAdministrationId(1L);
        dto.setComplexeSportifId(1L);
        dto.setEpreuveSportiveId(1L);
        dto.setHallId(1L);
        dto.setTarifIds(Set.of(1L));
        return dto;
    }
}