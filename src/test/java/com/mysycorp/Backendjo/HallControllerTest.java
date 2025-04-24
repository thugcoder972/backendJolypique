package com.mysycorp.Backendjo;

import com.mysycorp.Backendjo.controller.HallController;
import com.mysycorp.Backendjo.entity.ComplexeSportif;
import com.mysycorp.Backendjo.entity.Hall;
import com.mysycorp.Backendjo.repository.HallRepository;
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
class HallControllerTest {

    @Mock
    private HallRepository hallRepository;

    @InjectMocks
    private HallController hallController;

    private Hall hall;
    private ComplexeSportif complexe;

    @BeforeEach
    void setUp() {
        complexe = new ComplexeSportif();
        complexe.setId(1L);
        complexe.setNameComplexe("Complexe Sportif Paris");

        hall = new Hall();
        hall.setId(1L);
        hall.setName("Hall A");
        hall.setComplexeSportif(complexe);
    }

    @Test
    void getAllHalls_ShouldReturnAllHalls() {
        // Arrange
        when(hallRepository.findAll()).thenReturn(Arrays.asList(hall));

        // Act
        List<Hall> result = hallController.getAllHalls();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Hall A", result.get(0).getName());
        verify(hallRepository, times(1)).findAll();
    }

    @Test
    void getHallById_WhenExists_ShouldReturnHall() {
        // Arrange
        when(hallRepository.findById(1L)).thenReturn(Optional.of(hall));

        // Act
        ResponseEntity<Hall> response = hallController.getHallById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Hall A", response.getBody().getName());
        assertEquals(complexe, response.getBody().getComplexeSportif());
    }

    @Test
    void getHallById_WhenNotExists_ShouldReturnNotFound() {
        // Arrange
        when(hallRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Hall> response = hallController.getHallById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createHall_ShouldReturnCreatedHall() {
        // Arrange
        when(hallRepository.save(any(Hall.class))).thenReturn(hall);

        // Act
        Hall result = hallController.createHall(hall);

        // Assert
        assertNotNull(result);
        assertEquals("Hall A", result.getName());
        verify(hallRepository, times(1)).save(hall);
    }

    @Test
    void updateHall_WhenExists_ShouldReturnUpdatedHall() {
        // Arrange
        Hall updatedDetails = new Hall();
        updatedDetails.setName("Hall B");
        updatedDetails.setComplexeSportif(complexe);

        when(hallRepository.findById(1L)).thenReturn(Optional.of(hall));
        when(hallRepository.save(any(Hall.class))).thenReturn(hall);

        // Act
        ResponseEntity<Hall> response = hallController.updateHall(1L, updatedDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Hall B", response.getBody().getName());
        assertEquals(complexe, response.getBody().getComplexeSportif());
    }

    @Test
    void updateHall_WhenNotExists_ShouldReturnNotFound() {
        // Arrange
        Hall updatedDetails = new Hall();
        when(hallRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Hall> response = hallController.updateHall(1L, updatedDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(hallRepository, never()).save(any());
    }

    @Test
    void deleteHall_WhenExists_ShouldReturnOk() {
        // Arrange
        when(hallRepository.findById(1L)).thenReturn(Optional.of(hall));

        // Act
        ResponseEntity<Object> response = hallController.deleteHall(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(hallRepository, times(1)).delete(hall);
    }

    @Test
    void deleteHall_WhenNotExists_ShouldReturnNotFound() {
        // Arrange
        when(hallRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Object> response = hallController.deleteHall(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(hallRepository, never()).delete(any());
    }
}