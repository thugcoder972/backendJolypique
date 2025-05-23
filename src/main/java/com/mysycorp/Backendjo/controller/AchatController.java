package com.mysycorp.Backendjo.controller;

import com.mysycorp.Backendjo.dto.AchatDTO;
import com.mysycorp.Backendjo.dto.PaymentRequest;
import com.mysycorp.Backendjo.dto.TicketDTO;
import com.mysycorp.Backendjo.entity.Achat;
import com.mysycorp.Backendjo.entity.Ticket;
import com.mysycorp.Backendjo.entity.User;
import com.mysycorp.Backendjo.exception.ResourceNotFoundException;
import com.mysycorp.Backendjo.mapper.AchatMapper;
import com.mysycorp.Backendjo.repository.AchatRepository;
import com.mysycorp.Backendjo.repository.TicketRepository;
import com.mysycorp.Backendjo.repository.UserRepository;
import com.mysycorp.Backendjo.service.AchatService;

import io.jsonwebtoken.JwtException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/achats")
public class AchatController {

    private final AchatRepository achatRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final AchatMapper achatMapper;
   

    public AchatController(AchatRepository achatRepository,
                         UserRepository userRepository,
                         TicketRepository ticketRepository,
                         AchatMapper achatMapper ) {
        this.achatRepository = achatRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.achatMapper = achatMapper;
       
    }
    

    @PostMapping
    @Transactional
    public ResponseEntity<AchatDTO> createAchat(@RequestBody AchatDTO achatDTO) {
        User user = userRepository.findById(achatDTO.getUser())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + achatDTO.getUser()));

        List<Ticket> tickets = achatDTO.getTicketIds().stream()
                .map(ticketId -> ticketRepository.findById(ticketId)
                        .orElseThrow(() -> new ResourceNotFoundException("Ticket non trouvé avec l'ID: " + ticketId)))
                .collect(Collectors.toList());

        Achat achat = new Achat();
        achat.setUser(user);
        achat.setTickets(tickets);
        achat.setDateAchat(achatDTO.getDateAchat() != null ? achatDTO.getDateAchat() : LocalDateTime.now());
        achat.setNombreTickets(tickets.size());

        double prixTotal = tickets.stream()
                .flatMap(ticket -> ticket.getTarifs().stream())
                .mapToDouble(tarif -> tarif.getTarif())
                .sum();
        achat.setPrixTotal(prixTotal);

        Achat savedAchat = achatRepository.save(achat);
        return ResponseEntity.status(HttpStatus.CREATED).body(achatMapper.toDTO(savedAchat));
    }

    @GetMapping
    public ResponseEntity<List<AchatDTO>> getAllAchats() {
        List<Achat> achats = achatRepository.findAllWithTickets();
        return ResponseEntity.ok(achats.stream()
                .map(achatMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchatDTO> getAchatById(@PathVariable Long id) {
        return achatRepository.findByIdWithTickets(id)
                .map(achat -> ResponseEntity.ok(achatMapper.toDTO(achat)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AchatDTO>> getAchatsByUser(@PathVariable Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + userId);
        }
        
        List<Achat> achats = achatRepository.findByUserWithTickets(userId);
        
        return ResponseEntity.ok(achats.stream()
                .map(achatMapper::toDTO)
                .collect(Collectors.toList()));
            
    }
   @GetMapping("/user-achats")
public ResponseEntity<List<AchatDTO>> getUserAchats(
    @RequestHeader("Authorization") String authHeader) {
    
    try {
        // 1. Extraire directement le userId du token JWT
        String token = authHeader.replace("Bearer ", "");
        String userId = extractUserIdFromToken(token); // Méthode locale
        
        // 2. Récupérer l'utilisateur
        User user = userRepository.findById(Long.parseLong(userId))
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        
        // 3. Récupérer les achats
        return ResponseEntity.ok(
            achatRepository.findByUserWithTickets(user.getId()).stream()
                .map(achatMapper::toDTO)
                .collect(Collectors.toList())
        );
        
    } catch (JwtException | NumberFormatException e) {
        return ResponseEntity.badRequest().build();
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.notFound().build();
    } catch (Exception e) {
        return ResponseEntity.internalServerError().build();
    }
}

// Méthode utilitaire interne pour extraire le userId
private String extractUserIdFromToken(String jwtToken) {
    // Solution basique - à adapter selon votre structure JWT
    String[] parts = jwtToken.split("\\.");
    if (parts.length > 0) {
        String payload = new String(Base64.getDecoder().decode(parts[1]));
        return payload.split("\"sub\":\"")[1].split("\"")[0];
    }
    throw new JwtException("Token JWT invalide");
}

    @PostMapping("/{achatId}/pay")
    @Transactional
    public ResponseEntity<String> payAchat(
            @PathVariable Long achatId,
            @RequestBody PaymentRequest paymentRequest) {
        
        Achat achat = achatRepository.findByIdWithTickets(achatId)
                .orElseThrow(() -> new ResourceNotFoundException("Achat non trouvé avec l'ID: " + achatId));

        try {
            boolean paymentSuccess = processPayment(
                    paymentRequest.getCardNumber(),
                    paymentRequest.getExpiryDate(),
                    paymentRequest.getCvv(),
                    BigDecimal.valueOf(achat.getPrixTotal())
            );

            if (paymentSuccess) {
                achat.setPaymentStatus("PAID");
                achat.setTransactionId(UUID.randomUUID().toString());
                achat.setPaymentDate(LocalDateTime.now());
                achatRepository.save(achat);
                return ResponseEntity.ok("Paiement réussi. ID de transaction: " + achat.getTransactionId());
            } else {
                achat.setPaymentStatus("FAILED");
                achatRepository.save(achat);
                return ResponseEntity.badRequest().body("Échec du paiement");
            }
        } catch (Exception e) {
            achat.setPaymentStatus("ERROR");
            achatRepository.save(achat);
            return ResponseEntity.internalServerError()
                    .body("Erreur lors du traitement du paiement: " + e.getMessage());
        }
    }

    private boolean processPayment(String cardNumber, String expiryDate, String cvv, BigDecimal amount) {
        // Implémentation existante
        return true;
    }
}