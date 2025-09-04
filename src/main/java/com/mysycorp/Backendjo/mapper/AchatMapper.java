// package com.mysycorp.Backendjo.mapper;

// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.stereotype.Component;

// import com.mysycorp.Backendjo.dto.AchatDTO;
// import com.mysycorp.Backendjo.dto.TicketDTO;
// import com.mysycorp.Backendjo.entity.Achat;
// import com.mysycorp.Backendjo.entity.User;

// @Component
// public class AchatMapper {

//     private final TicketMapper ticketMapper; // Injectez votre TicketMapper

//     public AchatMapper(TicketMapper ticketMapper) {
//         this.ticketMapper = ticketMapper;
//     }

//     // Convertir un Achat en AchatDTO
//     public AchatDTO toDTO(Achat achat) {
//         AchatDTO dto = new AchatDTO();
//         dto.setId(achat.getId());
//         dto.setDateAchat(achat.getDateAchat());
//         dto.setUser(achat.getUser().getId());

//         // Convertir les tickets en TicketDTO
//         List<TicketDTO> ticketDTOs = achat.getTickets().stream()
//                                            .map(ticketMapper::toDTO)
//                 .collect(Collectors.toList());
//         dto.setTickets(ticketDTOs);

//         return dto;
//     }

//     // Convertir un AchatDTO en Achat
//     public Achat toEntity(AchatDTO achatDTO, User user) {
//         Achat achat = new Achat();
//         achat.setId(achatDTO.getId());
//         achat.setDateAchat(achatDTO.getDateAchat());

//             achat.setUser(user);
//         // Vous pouvez également gérer les tickets ici si nécessaire
//         return achat;
//     }
// }

package com.mysycorp.Backendjo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mysycorp.Backendjo.dto.AchatDTO;
import com.mysycorp.Backendjo.entity.Achat;
import com.mysycorp.Backendjo.entity.Ticket;
import com.mysycorp.Backendjo.entity.User;

@Component
public class AchatMapper {

    // Convertir un Achat → AchatDTO
    public AchatDTO toDTO(Achat achat) {
        AchatDTO dto = new AchatDTO();
        dto.setId(achat.getId());
        dto.setDateAchat(achat.getDateAchat());
        dto.setUser(achat.getUser().getId());

        // Récupérer uniquement les IDs des tickets
        List<Long> ticketIds = achat.getTickets().stream()
                                    .map(Ticket::getId)
                                    .collect(Collectors.toList());
        dto.setTicketIds(ticketIds);

        return dto;
    }

    // Convertir un AchatDTO → Achat (sans charger les tickets pour l’instant)
    public Achat toEntity(AchatDTO achatDTO, User user) {
        Achat achat = new Achat();
        achat.setId(achatDTO.getId());
        achat.setDateAchat(achatDTO.getDateAchat());
        achat.setUser(user);

        // ⚠️ Ici, on ne reconstruit pas directement les Tickets depuis les IDs
        // → ça se fait dans le service (via un TicketRepository.findAllById)
        
        return achat;
    }
}
