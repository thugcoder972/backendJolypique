package com.mysycorp.Backendjo;

import com.mysycorp.Backendjo.controller.TicketController;
import com.mysycorp.Backendjo.dto.TicketDTO;
import com.mysycorp.Backendjo.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    private TicketDTO ticketDTO;

    @BeforeEach
    void setUp() {
        ticketDTO = new TicketDTO();
        ticketDTO.setId(1L);
        ticketDTO.setStartTimeEpreuve(LocalDateTime.now());
        ticketDTO.setAchatId(100L);
        ticketDTO.setAdministrationId(200L);
        ticketDTO.setComplexeSportifId(300L);
        ticketDTO.setEpreuveSportiveId(400L);
        ticketDTO.setHallId(500L);
        
        Set<Long> tarifIds = new HashSet<>();
        tarifIds.add(10L);
        tarifIds.add(20L);
        ticketDTO.setTarifIds(tarifIds);
        
        ticketDTO.setRemainingPlaces(50);
        ticketDTO.setSeat("A12");
    }

    @Test
    void getAllTickets_ShouldReturnAllTickets() {
        // Arrange
        when(ticketService.getAllTickets()).thenReturn(Arrays.asList(ticketDTO));

        // Act
        List<TicketDTO> result = ticketController.getAllTickets();

        // Assert
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(50, result.get(0).getRemainingPlaces());
        verify(ticketService, times(1)).getAllTickets();
    }

    @Test
    void getTicketById_ShouldReturnTicket() {
        // Arrange
        when(ticketService.getTicketById(1L)).thenReturn(ticketDTO);

        // Act
        ResponseEntity<TicketDTO> response = ticketController.getTicketById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(300L, response.getBody().getComplexeSportifId());
        assertEquals(2, response.getBody().getTarifIds().size());
    }

    @Test
    void createTicket_ShouldReturnCreatedTicket() {
        // Arrange
        when(ticketService.createTicket(any(TicketDTO.class))).thenReturn(ticketDTO);

        // Act
        ResponseEntity<TicketDTO> response = ticketController.createTicket(ticketDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("A12", response.getBody().getSeat());
        verify(ticketService, times(1)).createTicket(ticketDTO);
    }

    @Test
    void updateTicket_ShouldReturnUpdatedTicket() {
        // Arrange
        TicketDTO updatedDetails = new TicketDTO();
        updatedDetails.setRemainingPlaces(30);
        updatedDetails.setSeat("B15");
        updatedDetails.setTarifIds(Set.of(30L, 40L));

        when(ticketService.updateTicket(eq(1L), any(TicketDTO.class))).thenReturn(updatedDetails);

        // Act
        ResponseEntity<TicketDTO> response = ticketController.updateTicket(1L, updatedDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(30, response.getBody().getRemainingPlaces());
        assertEquals("B15", response.getBody().getSeat());
        verify(ticketService, times(1)).updateTicket(1L, updatedDetails);
    }

    @Test
    void deleteTicket_ShouldReturnOk() {
        // Arrange
        doNothing().when(ticketService).deleteTicket(1L);

        // Act
        ResponseEntity<Void> response = ticketController.deleteTicket(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(ticketService, times(1)).deleteTicket(1L);
    }
}