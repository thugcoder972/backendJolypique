

package com.mysycorp.Backendjo.controller;

import com.mysycorp.Backendjo.dto.AchatDTO;
import com.mysycorp.Backendjo.dto.PaymentRequest;
import com.mysycorp.Backendjo.entity.Achat;
import com.mysycorp.Backendjo.entity.Ticket;
import com.mysycorp.Backendjo.entity.User;
import com.mysycorp.Backendjo.exception.ResourceNotFoundException;
import com.mysycorp.Backendjo.mapper.AchatMapper;
import com.mysycorp.Backendjo.repository.AchatRepository;
import com.mysycorp.Backendjo.repository.TicketRepository;
import com.mysycorp.Backendjo.repository.UserRepository;
import com.mysycorp.Backendjo.util.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/achats")
public class AchatController {

    private final AchatRepository achatRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final AchatMapper achatMapper;
    private final JwtTokenUtil jwtTokenUtil;

    public AchatController(AchatRepository achatRepository,
                          UserRepository userRepository,
                          TicketRepository ticketRepository,
                          AchatMapper achatMapper,
                          JwtTokenUtil jwtTokenUtil) {
        this.achatRepository = achatRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.achatMapper = achatMapper;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    // Endpoint pour créer un achat
   @PostMapping
@Transactional
public ResponseEntity<AchatDTO> createAchat(@RequestBody AchatDTO achatDTO) {

    // Vérifie que l'utilisateur est bien fourni
    if (achatDTO.getUser() == null) {
        return ResponseEntity.badRequest().build();
    }

    // Vérifie qu'au moins un ticket a été sélectionné
    if (achatDTO.getTicketIds() == null || achatDTO.getTicketIds().isEmpty()) {
        return ResponseEntity.badRequest()
                .body(null); // ou tu peux créer un DTO d'erreur avec un message explicite
    }

    // Recherche de l'utilisateur
    User user = userRepository.findById(achatDTO.getUser())
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + achatDTO.getUser()));

    // Recherche des tickets
    List<Ticket> tickets = achatDTO.getTicketIds().stream()
            .map(ticketId -> ticketRepository.findById(ticketId)
                    .orElseThrow(() -> new ResourceNotFoundException("Ticket non trouvé avec l'ID: " + ticketId)))
            .collect(Collectors.toList());

    // Création de l'achat
    Achat achat = new Achat();
    achat.setUser(user);
    achat.setTickets(tickets);
    achat.setDateAchat(achatDTO.getDateAchat() != null ? achatDTO.getDateAchat() : LocalDateTime.now());
    achat.setNombreTickets(tickets.size());

    // Calcul du prix total à partir des tarifs des tickets
    double prixTotal = tickets.stream()
            .flatMap(ticket -> ticket.getTarifs().stream())
            .mapToDouble(tarif -> tarif.getTarif())
            .sum();
    achat.setPrixTotal(prixTotal);

    // Enregistrement en base
    Achat savedAchat = achatRepository.save(achat);

    // Retourne l'objet DTO
    return ResponseEntity.status(HttpStatus.CREATED).body(achatMapper.toDTO(savedAchat));
}


    // Endpoint admin pour tous les achats
    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AchatDTO>> getAllAchats() {
        List<Achat> achats = achatRepository.findAllWithTickets();
        return ResponseEntity.ok(achats.stream()
                .map(achatMapper::toDTO)
                .collect(Collectors.toList()));
    }

    // Endpoint pour un achat spécifique
    @GetMapping("/{id}")
    public ResponseEntity<AchatDTO> getAchatById(@PathVariable Long id) {
        return achatRepository.findByIdWithTickets(id)
                .map(achat -> ResponseEntity.ok(achatMapper.toDTO(achat)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Version améliorée pour récupérer les achats par userId
    @GetMapping("/by-user")
    public ResponseEntity<List<AchatDTO>> getAchatsByUser(
            @RequestParam(required = false) Long userId,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        try {
            // Si userId n'est pas fourni, essayez de le récupérer depuis le token
            if (userId == null) {
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
                String token = authHeader.replace("Bearer ", "");
                userId = jwtTokenUtil.extractUserId(token);
            }

            // Vérifie que l'utilisateur existe
            if (!userRepository.existsById(userId)) {
                throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + userId);
            }

            List<Achat> achats = achatRepository.findByUserWithTickets(userId);
            return ResponseEntity.ok(achats.stream()
                    .map(achatMapper::toDTO)
                    .collect(Collectors.toList()));

        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Ancien endpoint conservé pour compatibilité (à déprécier à terme)
    @GetMapping("/user-achats")
    @Deprecated
    public ResponseEntity<List<AchatDTO>> getUserAchats(
            @RequestHeader("Authorization") String authHeader) {
        return getAchatsByUser(null, authHeader);
    }

    // Endpoint pour payer un achat
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