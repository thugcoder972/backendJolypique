package com.mysycorp.Backendjo;

import com.mysycorp.Backendjo.controller.EpreuveSportiveController;
import com.mysycorp.Backendjo.dto.EpreuveSportiveDTO;
import com.mysycorp.Backendjo.entity.EpreuveSportive;
import com.mysycorp.Backendjo.entity.Hall;
import com.mysycorp.Backendjo.repository.EpreuveSportiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EpreuveSportiveControllerTest {

    @Mock
    private EpreuveSportiveRepository epreuveSportiveRepository;

    @InjectMocks
    private EpreuveSportiveController controller;

    private EpreuveSportive epreuveSportive;
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        Hall hall = new Hall();
        hall.setName("Stade Olympique");
        
        epreuveSportive = new EpreuveSportive();
        epreuveSportive.setId(1L);
        epreuveSportive.setNameEpreuveSportive("100m Sprint");
        epreuveSportive.setTypeEpreuveSportive("Athlétisme");
        epreuveSportive.setDurationInSeconds(60L);
        epreuveSportive.setHall(hall);
        epreuveSportive.setTicketPrice(50.0);
        
        bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    void getEpreuveById_WhenEpreuveExists_ShouldReturnEpreuveDTO() {
        when(epreuveSportiveRepository.findById(1L)).thenReturn(Optional.of(epreuveSportive));
        
        ResponseEntity<EpreuveSportiveDTO> response = controller.getEpreuveById(1L);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(60L, response.getBody().getDurationInSeconds());
    }

    @Test
    void getEpreuveById_WhenEpreuveNotExists_ShouldReturnNotFound() {
        when(epreuveSportiveRepository.findById(1L)).thenReturn(Optional.empty());
        
        ResponseEntity<EpreuveSportiveDTO> response = controller.getEpreuveById(1L);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createEpreuve_WithValidData_ShouldReturnCreatedEpreuve() {
        when(epreuveSportiveRepository.save(any())).thenReturn(epreuveSportive);
        
        ResponseEntity<EpreuveSportiveDTO> response = controller.createEpreuve(epreuveSportive, bindingResult);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(60L, response.getBody().getDurationInSeconds());
    }

    @Test
    void createEpreuve_WithInvalidData_ShouldReturnBadRequest() {
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of(new ObjectError("epreuve", "error")));
        
        ResponseEntity<EpreuveSportiveDTO> response = controller.createEpreuve(new EpreuveSportive(), bindingResult);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void updateEpreuve_WhenEpreuveExists_ShouldReturnUpdatedEpreuve() {
        EpreuveSportive updatedEpreuve = new EpreuveSportive();
        updatedEpreuve.setDurationInSeconds(120L);
        
        when(epreuveSportiveRepository.findById(1L)).thenReturn(Optional.of(epreuveSportive));
        when(epreuveSportiveRepository.save(any())).thenReturn(updatedEpreuve);
        
        ResponseEntity<EpreuveSportiveDTO> response = controller.updateEpreuve(1L, updatedEpreuve, bindingResult);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(120L, response.getBody().getDurationInSeconds());
    }

    @Test
    void getAllEpreuves_ShouldReturnListOfEpreuveDTOs() {
        when(epreuveSportiveRepository.findAll()).thenReturn(List.of(epreuveSportive));
        
        ResponseEntity<List<EpreuveSportiveDTO>> response = controller.getAllEpreuves();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(60L, response.getBody().get(0).getDurationInSeconds());
    }

    @Test
    void getEpreuvesByType_ShouldReturnFilteredList() {
        when(epreuveSportiveRepository.findByTypeEpreuveSportive("Athlétisme"))
            .thenReturn(List.of(epreuveSportive));
        
        ResponseEntity<List<EpreuveSportiveDTO>> response = controller.getEpreuvesByType("Athlétisme");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(60L, response.getBody().get(0).getDurationInSeconds());
    }
}