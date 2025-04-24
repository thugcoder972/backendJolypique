package com.mysycorp.Backendjo.repository;

import com.mysycorp.Backendjo.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    // Méthode pour charger un ticket avec ses tarifs
    @Query("SELECT t FROM Ticket t LEFT JOIN FETCH t.tarifs WHERE t.id = :id")
    Optional<Ticket> findByIdWithTarifs(@Param("id") Long id);
    
    // Méthode pour charger les tickets d'un achat avec leurs tarifs
    @Query("SELECT t FROM Ticket t LEFT JOIN FETCH t.tarifs WHERE t.achat.id = :achatId")
    List<Ticket> findByAchatIdWithTarifs(@Param("achatId") Long achatId);
}