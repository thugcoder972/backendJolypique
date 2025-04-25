package com.mysycorp.Backendjo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysycorp.Backendjo.dto.AchatDTO;
import com.mysycorp.Backendjo.dto.TicketDTO;
import com.mysycorp.Backendjo.entity.Achat;
import com.mysycorp.Backendjo.entity.Ticket;
import com.mysycorp.Backendjo.entity.User;
import com.mysycorp.Backendjo.mapper.AchatMapper;
import com.mysycorp.Backendjo.repository.AchatRepository;
import com.mysycorp.Backendjo.repository.TicketRepository;
import com.mysycorp.Backendjo.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Utilise le profil "test" pour la configuration
public class AchatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AchatRepository achatRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private AchatMapper achatMapper;

    private User testUser;
    private Ticket testTicket;
    private Achat testAchat;
    private AchatDTO testAchatDTO;
    private TicketDTO testTicketDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        testTicket = new Ticket();
        testTicket.setId(1L);

        testAchat = new Achat();
        testAchat.setId(1L);
        testAchat.setUser(testUser);
        testAchat.setDateAchat(LocalDateTime.now());
        testAchat.setNombreTickets(1);
        testAchat.setPrixTotal(100.0);
        testAchat.setTickets(List.of(testTicket));

        testTicketDTO = new TicketDTO();
        testTicketDTO.setId(1L);

        testAchatDTO = new AchatDTO();
        testAchatDTO.setId(1L);
        testAchatDTO.setUser(1L);
        testAchatDTO.setDateAchat(LocalDateTime.now());
        testAchatDTO.setTickets(List.of(testTicketDTO));
    }

    @Test
    public void createAchat_ShouldReturnCreated() throws Exception {
        when(userRepository.getReferenceById(1L)).thenReturn(testUser);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
        when(achatRepository.save(any(Achat.class))).thenReturn(testAchat);
        when(achatMapper.toDTO(any(Achat.class))).thenReturn(testAchatDTO);

        mockMvc.perform(post("/api/achats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAchatDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }
}