package com.mysycorp.Backendjo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mysycorp.Backendjo.entity.Achat;

@Repository
public interface AchatRepository extends JpaRepository<Achat, Long>{

// Ajoutez cette méthode pour résoudre le problème Lazy
    @Query("SELECT DISTINCT a FROM Achat a LEFT JOIN FETCH a.tickets WHERE a.id = :id")
    Optional<Achat> findByIdWithTickets(@Param("id") Long id);
    
    // Ajoutez aussi cette variante pour getAllAchats
    @Query("SELECT DISTINCT a FROM Achat a LEFT JOIN FETCH a.tickets")
    List<Achat> findAllWithTickets();    
    @Query("SELECT a FROM Achat a LEFT JOIN FETCH a.tickets WHERE a.user.id = :userId")
List<Achat> findByUserWithTickets(@Param("userId") Long userId);
// Rechercher tous les achats par un utilisateur donné
List<Achat> findByUser_Id(Long userId);

// Rechercher tous les achats dans une plage de dates
List<Achat> findByDateAchatBetween(LocalDateTime startDate, LocalDateTime endDate);

// Rechercher tous les achats par nombre de tickets
List<Achat> findByNombreTickets(int nombreTickets);

// Rechercher tous les achats par prix total
List<Achat> findByPrixTotalGreaterThanEqual(double prixTotal);
}
