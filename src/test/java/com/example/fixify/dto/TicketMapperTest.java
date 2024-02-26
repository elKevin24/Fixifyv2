package com.example.fixify.dto;

import com.example.fixify.models.Ticket;
import com.example.fixify.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.itextpdf.kernel.pdf.PdfName.Desc;
import static com.itextpdf.kernel.pdf.PdfName.Sort;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class TicketMapperTest {


    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Test
    void toDto() {


        // Obtener todos los tickets de la base de datos
        Optional<Ticket> tickets = ticketRepository.findById(149L);


        // Asegurarse de que la lista de DTOs no esté vacía si hay tickets en la base de datos
        if (!tickets.isEmpty()) {
            assertFalse(tickets.isEmpty(), "La lista de TicketDto no debería estar vacía");
            // Convertir la lista de tickets a TicketDto usando el mapper
            TicketDto ticketDto = ticketMapper.toDto(
                    tickets.get()
            );
            System.out.println("ticketDtos = " + ticketDto);
        }

    }

    @Test
    void fromDto() {
    }
}