package com.mysycorp.Backendjo.repository;

import com.mysycorp.Backendjo.entity.Administration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdministrationRepository extends JpaRepository<Administration, Long> {

   @Query("SELECT DISTINCT a FROM Administration a LEFT JOIN FETCH a.complexes LEFT JOIN FETCH a.tickets WHERE a.id = :id")
    Optional<Administration> findByIdWithRelations(@Param("id") Long id); 
    @Query("SELECT DISTINCT a FROM Administration a LEFT JOIN FETCH a.complexes")
    List<Administration> findAllWithComplexes();
}