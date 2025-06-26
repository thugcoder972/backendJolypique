package com.mysycorp.Backendjo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysycorp.Backendjo.dto.AchatDTO;
import com.mysycorp.Backendjo.entity.Achat;
import com.mysycorp.Backendjo.service.AchatService;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled 
public class AchatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AchatService achatService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createAchat_ShouldReturnCreated() throws Exception {
        AchatDTO dto = new AchatDTO();
        dto.setUser(1L);
        dto.setTicketIds(new HashSet<>()); // Assure-toi que c'est un Set<Long>

        Achat achat = new Achat();

        when(achatService.createAchat(any())).thenReturn(achat);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/achats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}
