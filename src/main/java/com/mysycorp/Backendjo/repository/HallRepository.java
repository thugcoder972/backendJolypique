package com.mysycorp.Backendjo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mysycorp.Backendjo.entity.Hall;

@Repository
public interface HallRepository extends JpaRepository <Hall, Object> {
@Query("SELECT h FROM Hall h LEFT JOIN FETCH h.complexeSportif WHERE h.id = :id")
Hall findByIdWithComplexe(@Param("id") Long id);
}
