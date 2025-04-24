package com.mysycorp.Backendjo;

import com.mysycorp.Backendjo.controller.AchatController;
import com.mysycorp.Backendjo.entity.Achat;
import com.mysycorp.Backendjo.entity.User;
import com.mysycorp.Backendjo.repository.AchatRepository;
import com.mysycorp.Backendjo.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AchatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AchatRepository achatRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AchatController achatController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(achatController).build();
    }

    @Test
    public void getAchatById_WhenNotExists_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(achatRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/achats/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAchatById_WhenExists_ShouldReturnAchat() throws Exception {
        // Arrange
        Achat achat = new Achat();
        achat.setId(1L);
        when(achatRepository.findById(1L)).thenReturn(Optional.of(achat));

        // Act & Assert
        mockMvc.perform(get("/api/achats/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
   
}