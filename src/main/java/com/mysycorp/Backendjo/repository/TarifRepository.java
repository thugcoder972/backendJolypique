package com.mysycorp.Backendjo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mysycorp.Backendjo.entity.Tarif;

@Repository
public interface TarifRepository extends JpaRepository<Tarif, Long> {

   // Dans TarifRepository, ajoutez :
@Query("SELECT COUNT(t) > 0 FROM Tarif t WHERE t.id = :id")
boolean existsById(@Param("id") Long id); 
}
