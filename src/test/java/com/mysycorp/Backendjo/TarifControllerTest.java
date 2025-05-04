package com.mysycorp.Backendjo;  // Package corrigé

import com.mysycorp.Backendjo.controller.TarifController;
import com.mysycorp.Backendjo.dto.TarifDTO;
import com.mysycorp.Backendjo.entity.Tarif;
import com.mysycorp.Backendjo.repository.TarifRepository;
import com.mysycorp.Backendjo.service.TarifService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TarifControllerTest {

    @Mock
    private TarifRepository tarifRepository;

    @Mock
    private TarifService tarifService;

    @InjectMocks
    private TarifController tarifController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTarif_ShouldReturnCreated() {
        // Given
        TarifDTO dto = new TarifDTO(null, "VIP", "Offre spéciale", 100.0);
        Tarif savedTarif = createTestTarif(1L, "VIP", "Offre spéciale", 100.0);
        TarifDTO expectedDto = new TarifDTO(1L, "VIP", "Offre spéciale", 100.0);

        when(tarifService.convertToEntity(dto)).thenReturn(savedTarif);
        when(tarifRepository.save(savedTarif)).thenReturn(savedTarif);
        when(tarifService.convertToDto(savedTarif)).thenReturn(expectedDto);

        // When
        ResponseEntity<?> response = tarifController.createTarif(dto);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
    }

    @Test
    void getAllTarifs_ShouldReturnAllTarifs() {
        // Given
        Tarif tarif1 = createTestTarif(1L, "Standard", "Basique", 50.0);
        Tarif tarif2 = createTestTarif(2L, "Premium", "Avancé", 100.0);
        List<Tarif> tarifs = Arrays.asList(tarif1, tarif2);

        TarifDTO dto1 = new TarifDTO(1L, "Standard", "Basique", 50.0);
        TarifDTO dto2 = new TarifDTO(2L, "Premium", "Avancé", 100.0);
        List<TarifDTO> expectedDtos = Arrays.asList(dto1, dto2);

        when(tarifRepository.findAll()).thenReturn(tarifs);
        when(tarifService.convertToDto(tarif1)).thenReturn(dto1);
        when(tarifService.convertToDto(tarif2)).thenReturn(dto2);

        // When
        ResponseEntity<List<TarifDTO>> response = tarifController.getAllTarifs();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDtos, response.getBody());
    }

    @Test
    void getTarifById_ShouldReturnTarif_WhenExists() {
        // Given
        Long id = 1L;
        Tarif tarif = createTestTarif(id, "Standard", "Basique", 50.0);
        TarifDTO expectedDto = new TarifDTO(id, "Standard", "Basique", 50.0);

        when(tarifRepository.findById(id)).thenReturn(Optional.of(tarif));
        when(tarifService.convertToDto(tarif)).thenReturn(expectedDto);

        // When
        ResponseEntity<TarifDTO> response = tarifController.getTarifById(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
    }

    // Méthode utilitaire pour créer des objets Tarif
    private Tarif createTestTarif(Long id, String name, String offre, double tarif) {
        Tarif t = new Tarif();
        t.setId(id);
        t.setNameTarif(name);
        t.setOffreTarif(offre);
        t.setTarif(tarif);
        return t;
    }
}