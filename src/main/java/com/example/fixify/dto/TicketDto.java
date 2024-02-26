package com.example.fixify.dto;

import com.example.fixify.models.ServicesTicket;
import com.example.fixify.models.Ticket;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link Ticket}
 */
@Value
public class TicketDto implements Serializable {
    Long id;
    String description;
    String technicalReview;
    LocalDateTime creationDate;
    List<ServicesTicketDto> servicios;

    /**
     * DTO for {@link ServicesTicket}
     */
    @Value
    public static class ServicesTicketDto implements Serializable {
        private Long id;
        String description;
        BigDecimal price;
        List<PartDto> parts; // Lista de partes asociadas
        @Value
        public static class PartDto implements Serializable {
            Long id;
            String description;
            BigDecimal price;
            // Otros campos según sea necesario
        }
    }
}