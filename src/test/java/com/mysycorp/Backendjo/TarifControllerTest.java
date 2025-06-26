package com.mysycorp.Backendjo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysycorp.Backendjo.controller.TarifController;
import com.mysycorp.Backendjo.dto.TarifDTO;
import com.mysycorp.Backendjo.entity.Tarif;
import com.mysycorp.Backendjo.service.TarifService;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled
public class TarifControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TarifService tarifService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createTarif_ShouldReturnCreated() throws Exception {
        TarifDTO dto = new TarifDTO();
        Tarif entity = new Tarif();

        when(tarifService.convertToEntity(any())).thenReturn(entity);
        when(tarifService.saveTarif(any(Tarif.class))).thenReturn(entity);
        when(tarifService.convertToDto(any())).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/tarifs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllTarifs_ShouldReturnAllTarifs() throws Exception {
        Tarif tarif = new Tarif();
        TarifDTO dto = new TarifDTO();

        when(tarifService.getAllTarifs()).thenReturn(Collections.singletonList(tarif));
        when(tarifService.convertToDto(tarif)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tarifs"))
                .andExpect(status().isOk());
    }

    @Test
    void getTarifById_ShouldReturnTarif_WhenExists() throws Exception {
        Tarif tarif = new Tarif();
        TarifDTO dto = new TarifDTO();

        when(tarifService.getTarifById(1L)).thenReturn(Optional.of(tarif));
        when(tarifService.convertToDto(tarif)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tarifs/1"))
                .andExpect(status().isOk());
    }
}
