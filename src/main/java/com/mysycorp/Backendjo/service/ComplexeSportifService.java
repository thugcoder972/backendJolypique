package com.mysycorp.Backendjo.service;

import com.mysycorp.Backendjo.dto.ComplexeSportifDTO;
import com.mysycorp.Backendjo.repository.ComplexeSportifRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional // Retirez readOnly qui n'existe pas dans jakarta.transaction.Transactional
public class ComplexeSportifService {

    private final ComplexeSportifRepository complexeRepository;

    public ComplexeSportifService(ComplexeSportifRepository complexeRepository) {
        this.complexeRepository = complexeRepository;
    }

    public List<ComplexeSportifDTO> getAllComplexes() {
        return complexeRepository.findAllWithAssociations().stream()
            .map(ComplexeSportifDTO::fromEntity)
            .collect(Collectors.toList());
    }
}
