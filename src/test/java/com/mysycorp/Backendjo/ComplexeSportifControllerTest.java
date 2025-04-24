package com.mysycorp.Backendjo;

import com.mysycorp.Backendjo.controller.ComplexeSportifController;
import com.mysycorp.Backendjo.entity.Administration;
import com.mysycorp.Backendjo.entity.ComplexeSportif;
import com.mysycorp.Backendjo.entity.Hall;
import com.mysycorp.Backendjo.entity.Ticket;
import com.mysycorp.Backendjo.repository.ComplexeSportifRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplexeSportifControllerTest {

    @Mock
    private ComplexeSportifRepository complexeSportifRepository;

    @InjectMocks
    private ComplexeSportifController complexeSportifController;

    private ComplexeSportif complexe;
    private Administration administration;

    @BeforeEach
    void setUp() {
        administration = new Administration();
        administration.setId(1L);
        
        complexe = new ComplexeSportif();
        complexe.setId(1L);
        complexe.setNameComplexe("Stade de France");
        complexe.setAdresseComplexe("Paris");
        complexe.setAdministration(administration);
        complexe.setHalls(new HashSet<>());
        complexe.setTickets(new HashSet<>());
    }

    @Test
    void getAllComplexes_ShouldReturnAllComplexes() {
        // Arrange
        when(complexeSportifRepository.findAll()).thenReturn(Arrays.asList(complexe));

        // Act
        List<ComplexeSportif> result = complexeSportifController.getAllComplexes();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Stade de France", result.get(0).getNameComplexe());
        verify(complexeSportifRepository, times(1)).findAll();
    }

    @Test
    void getComplexeById_WhenExists_ShouldReturnComplexe() {
        // Arrange
        when(complexeSportifRepository.findById(1L)).thenReturn(Optional.of(complexe));

        // Act
        ResponseEntity<ComplexeSportif> response = complexeSportifController.getComplexeById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Stade de France", response.getBody().getNameComplexe());
        assertEquals("Paris", response.getBody().getAdresseComplexe());
    }

    @Test
    void createComplexe_ShouldReturnCreatedComplexe() {
        // Arrange
        when(complexeSportifRepository.save(any(ComplexeSportif.class))).thenReturn(complexe);

        // Act
        ComplexeSportif result = complexeSportifController.createComplexe(complexe);

        // Assert
        assertNotNull(result);
        assertEquals("Stade de France", result.getNameComplexe());
        verify(complexeSportifRepository, times(1)).save(complexe);
    }

    @Test
    void updateComplexe_WhenExists_ShouldReturnUpdatedComplexe() {
        // Arrange
        ComplexeSportif updatedDetails = new ComplexeSportif();
        updatedDetails.setNameComplexe("Nouveau Stade");
        updatedDetails.setAdresseComplexe("Lyon");
        updatedDetails.setAdministration(administration);

        when(complexeSportifRepository.findById(1L)).thenReturn(Optional.of(complexe));
        when(complexeSportifRepository.save(any(ComplexeSportif.class))).thenReturn(complexe);

        // Act
        ResponseEntity<ComplexeSportif> response = 
            complexeSportifController.updateComplexe(1L, updatedDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Nouveau Stade", response.getBody().getNameComplexe());
        assertEquals("Lyon", response.getBody().getAdresseComplexe());
    }

    // ... (les autres méthodes de test restent similaires avec les adaptations nécessaires)
}