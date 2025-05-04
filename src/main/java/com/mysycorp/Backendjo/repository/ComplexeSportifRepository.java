package com.mysycorp.Backendjo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mysycorp.Backendjo.entity.ComplexeSportif;

@Repository
public interface ComplexeSportifRepository extends JpaRepository<ComplexeSportif, Long>{
@Query("SELECT DISTINCT c FROM ComplexeSportif c " +
           "LEFT JOIN FETCH c.administration " +
           "LEFT JOIN FETCH c.halls")
    List<ComplexeSportif> findAllWithAssociations();
}
