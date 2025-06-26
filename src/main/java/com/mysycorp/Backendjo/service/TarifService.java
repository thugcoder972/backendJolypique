package com.mysycorp.Backendjo.service;

import com.mysycorp.Backendjo.dto.TarifDTO;
import com.mysycorp.Backendjo.entity.Tarif;
import com.mysycorp.Backendjo.repository.TarifRepository;
import com.mysycorp.Backendjo.repository.TicketRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarifService {

    private final TarifRepository tarifRepository;
    private final TicketRepository ticketRepository;

    // Constructeur d'injection
    public TarifService(TarifRepository tarifRepository, TicketRepository ticketRepository) {
        this.tarifRepository = tarifRepository;
        this.ticketRepository = ticketRepository;
    }

    // ✅ Convert DTO → Entity
    public Tarif convertToEntity(TarifDTO dto) {
        Tarif tarif = new Tarif();
        tarif.setNameTarif(dto.getNameTarif());
        tarif.setOffreTarif(dto.getOffreTarif());
        tarif.setTarif(dto.getTarif());
        return tarif;
    }

    // ✅ Convert Entity → DTO
    public TarifDTO convertToDto(Tarif entity) {
        return new TarifDTO(
            entity.getId(),
            entity.getNameTarif(),
            entity.getOffreTarif(),
            entity.getTarif()
        );
    }

    // ✅ Méthodes manquantes à ajouter :

    public Tarif saveTarif(Tarif tarif) {
        return tarifRepository.save(tarif);
    }

    public List<Tarif> getAllTarifs() {
        return tarifRepository.findAll();
    }

    public Optional<Tarif> getTarifById(Long id) {
        return tarifRepository.findById(id);
    }
}

