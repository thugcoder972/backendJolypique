package com.mysycorp.Backendjo.repository;

import com.mysycorp.Backendjo.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @EntityGraph(attributePaths = {"administration", "complexeSportif", "epreuveSportive", "hall", "tarifs"})
    List<Ticket> findAll();

    @EntityGraph(attributePaths = {"administration", "complexeSportif", "epreuveSportive", "hall", "tarifs"})
    Optional<Ticket> findById(Long id);

    // Alternative avec JOIN FETCH explicite
    @Query("SELECT DISTINCT t FROM Ticket t LEFT JOIN FETCH t.tarifs LEFT JOIN FETCH t.administration LEFT JOIN FETCH t.complexeSportif LEFT JOIN FETCH t.epreuveSportive LEFT JOIN FETCH t.hall WHERE t.id = :id")
    Optional<Ticket> findByIdWithAllAssociations(@Param("id") Long id);
}
