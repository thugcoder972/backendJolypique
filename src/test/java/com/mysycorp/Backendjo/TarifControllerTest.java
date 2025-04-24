package com.mysycorp.Backendjo;

import com.mysycorp.Backendjo.controller.TarifController;
import com.mysycorp.Backendjo.entity.Tarif;
import com.mysycorp.Backendjo.repository.TarifRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarifControllerTest {

    @Mock
    private TarifRepository tarifRepository;

    @InjectMocks
    private TarifController tarifController;

    private Tarif tarif;

    @BeforeEach
    void setUp() {
        tarif = new Tarif();
        tarif.setId(1L);
        tarif.setNameTarif("Tarif Plein");
        tarif.setTarif(20.0);
        tarif.setOffreTarif("Offre Standard");
    }

    @Test
    void getAllTarifs_ShouldReturnAllTarifs() {
        // Arrange
        when(tarifRepository.findAll()).thenReturn(Arrays.asList(tarif));

        // Act
        List<Tarif> result = tarifController.getAllTarifs();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Tarif Plein", result.get(0).getNameTarif());
        verify(tarifRepository, times(1)).findAll();
    }

    @Test
    void getTarifById_WhenExists_ShouldReturnTarif() {
        // Arrange
        when(tarifRepository.findById(1L)).thenReturn(Optional.of(tarif));

        // Act
        ResponseEntity<Tarif> response = tarifController.getTarifById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Tarif Plein", response.getBody().getNameTarif());
        assertEquals(20.0, response.getBody().getTarif());
    }

    @Test
    void getTarifById_WhenNotExists_ShouldReturnNotFound() {
        // Arrange
        when(tarifRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Tarif> response = tarifController.getTarifById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createTarif_ShouldReturnCreatedTarif() {
        // Arrange
        when(tarifRepository.save(any(Tarif.class))).thenReturn(tarif);

        // Act
        Tarif result = tarifController.createTarif(tarif);

        // Assert
        assertNotNull(result);
        assertEquals("Tarif Plein", result.getNameTarif());
        verify(tarifRepository, times(1)).save(tarif);
    }

    @Test
    void updateTarif_WhenExists_ShouldReturnUpdatedTarif() {
        // Arrange
        Tarif updatedDetails = new Tarif();
        updatedDetails.setNameTarif("Tarif Réduit");
        updatedDetails.setTarif(15.0);
        updatedDetails.setOffreTarif("Offre Spéciale");

        when(tarifRepository.findById(1L)).thenReturn(Optional.of(tarif));
        when(tarifRepository.save(any(Tarif.class))).thenReturn(tarif);

        // Act
        ResponseEntity<Tarif> response = tarifController.updateTarif(1L, updatedDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Tarif Réduit", response.getBody().getNameTarif());
        assertEquals(15.0, response.getBody().getTarif());
        assertEquals("Offre Spéciale", response.getBody().getOffreTarif());
    }

    @Test
    void updateTarif_WhenNotExists_ShouldReturnNotFound() {
        // Arrange
        Tarif updatedDetails = new Tarif();
        when(tarifRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Tarif> response = tarifController.updateTarif(1L, updatedDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(tarifRepository, never()).save(any());
    }

    @Test
    void deleteTarif_WhenExists_ShouldReturnOk() {
        // Arrange
        when(tarifRepository.findById(1L)).thenReturn(Optional.of(tarif));

        // Act
        ResponseEntity<Object> response = tarifController.deleteTarif(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(tarifRepository, times(1)).delete(tarif);
    }

    @Test
    void deleteTarif_WhenNotExists_ShouldReturnNotFound() {
        // Arrange
        when(tarifRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Object> response = tarifController.deleteTarif(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(tarifRepository, never()).delete(any());
    }
}