package com.mysycorp.Backendjo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mysycorp.Backendjo.entity.EpreuveSportive;

@Repository
public interface EpreuveSportiveRepository extends JpaRepository<EpreuveSportive, Long>{

@Query("SELECT t.id FROM Ticket t WHERE t.epreuveSportive.id = :epreuveId")
Optional<Long> findTicketIdByEpreuveId(@Param("epreuveId") Long epreuveId);
    List<EpreuveSportive> findByTypeEpreuveSportive(String type);

}
