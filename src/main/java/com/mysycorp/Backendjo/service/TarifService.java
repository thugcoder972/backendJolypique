package com.mysycorp.Backendjo.service;

import com.mysycorp.Backendjo.dto.TarifDTO;
import com.mysycorp.Backendjo.entity.Tarif;
import com.mysycorp.Backendjo.entity.Ticket;
import com.mysycorp.Backendjo.repository.TarifRepository;
import com.mysycorp.Backendjo.repository.TicketRepository;

import org.springframework.stereotype.Service;

@Service
public class TarifService {
    
    public Tarif convertToEntity(TarifDTO dto) {
        Tarif tarif = new Tarif();
        tarif.setNameTarif(dto.getNameTarif());
        tarif.setOffreTarif(dto.getOffreTarif());
        tarif.setTarif(dto.getTarif());
        return tarif;
    }
    
    public TarifDTO convertToDto(Tarif entity) {
        return new TarifDTO(
            entity.getId(),
            entity.getNameTarif(),
            entity.getOffreTarif(),
            entity.getTarif()
        );
    }


    private final TarifRepository tarifRepository;
    private final TicketRepository ticketRepository; // Ajout du repository manquant

    // Injection par constructeur
    public TarifService(TarifRepository tarifRepository, 
                       TicketRepository ticketRepository) {
        this.tarifRepository = tarifRepository;
        this.ticketRepository = ticketRepository;
    }

    // ... vos autres méthodes
}
   

