package com.mysycorp.Backendjo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysycorp.Backendjo.dto.AchatDTO;
import com.mysycorp.Backendjo.entity.Achat;
import com.mysycorp.Backendjo.entity.Ticket;
import com.mysycorp.Backendjo.entity.User;
import com.mysycorp.Backendjo.mapper.AchatMapper;
import com.mysycorp.Backendjo.repository.AchatRepository;
import com.mysycorp.Backendjo.repository.TicketRepository;
import com.mysycorp.Backendjo.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AchatService {

    private final AchatRepository achatRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final AchatMapper achatMapper;

    public AchatService(AchatRepository achatRepository, UserRepository userRepository, 
                        TicketRepository ticketRepository, AchatMapper achatMapper) {
        this.achatRepository = achatRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.achatMapper = achatMapper;
    }

    @Transactional(readOnly = true)
    public List<AchatDTO> getAllAchats() {
        List<Achat> achats = achatRepository.findAll();
        return achats.stream().map(achatMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AchatDTO getAchatById(Long id) {
        Achat achat = achatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Achat not found with id " + id));
        return achatMapper.toDTO(achat);
    }

    // @Transactional
    // public AchatDTO createAchat(AchatDTO achatDTO) {
    //     User user = userRepository.findById(achatDTO.getUserId())
    //             .orElseThrow(() -> new EntityNotFoundException("User not found with id " + achatDTO.getUserId()));
    //             // Récupère les tickets et les convertit en Set
    //             List<Ticket> ticketList = ticketRepository.findAllById(achatDTO.getTicketIds());
    //             Set<Ticket> tickets = new HashSet<>(ticketList);  // Convertir la liste en Set
                
    //     Achat achat = achatMapper.toEntity(achatDTO, user, tickets);
    //     Achat savedAchat = achatRepository.save(achat);
    //     return achatMapper.toDTO(savedAchat);
    //}

    @Transactional(readOnly = true)
    public List<Achat> findAll() {
        return achatRepository.findAll(); // Appeler le repository pour obtenir tous les achats
}

    public Object createAchat(AchatDTO any) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createAchat'");
    }

    public void deleteAchat(long l) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAchat'");
    }

    public Object save(Achat any) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
}

