// package com.mysycorp.Backendjo.dto;

// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Set;
// import java.util.stream.Collectors;

// public class AchatDTO {
//     private Long id;
//     private Long user;  // Référence à l'utilisateur qui a fait l'achat
//     private List<TicketDTO> tickets;  // Liste des tickets associés à l'achat
//     private LocalDateTime dateAchat;  // Date de l'achat
    


//     // Getters and Setters
//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public Long getUser() {
//         return user;
//     }

//     public void setUser(Long user) {
//         this.user = user;
//     }

//     public List<TicketDTO> getTickets() {
//         return tickets;
//     }

//     public void setTickets(List<TicketDTO> tickets) {
//         this.tickets = tickets;
//     }

//     public LocalDateTime getDateAchat() {
//         return dateAchat;
//     }

//     public void setDateAchat(LocalDateTime dateAchat) {
//         this.dateAchat = dateAchat;
//     }

// // Implémentation de getTicketIds
//     public Set<Long> getTicketIds() {
//         // Récupérer les IDs des tickets associés
//         return tickets.stream()
//                       .map(TicketDTO::getId)
//                       .collect(Collectors.toSet());
//     }

//     // Implémentation de setTicketIds
//     public void setTicketIds(Set<Long> ticketIds) {
//         // Créer une liste de TicketDTO à partir des IDs (nécessite un constructeur ou un setter dans TicketDTO)
//         this.tickets = ticketIds.stream()
//                                 .map(id -> {
//                                     TicketDTO ticket = new TicketDTO();
//                                     ticket.setId(id);
//                                     return ticket;
//                                 })
//                                 .collect(Collectors.toList());
//     }

// }
package com.mysycorp.Backendjo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AchatDTO {
    private Long id;
    private Long user;                // ID de l'utilisateur
    private List<Long> ticketIds;     // Liste des IDs des tickets associés
    private LocalDateTime dateAchat;  // Date de l'achat

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public List<Long> getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(List<Long> ticketIds) {
        this.ticketIds = ticketIds;
    }

    public LocalDateTime getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDateTime dateAchat) {
        this.dateAchat = dateAchat;
    }
}
