package com.example.fixify.service;

import com.example.fixify.models.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class TicketServiceIntegrationTest {

    @Autowired
    private TicketService ticketService;


    @Test
    @Transactional
    void findOneByIdTest() {
        // Asegúrate de que este ID exista en tu base de datos de desarrollo
        Long ticketId = 144L;
        Optional<Ticket> result = ticketService.findOneById(ticketId);

        assertTrue(result.isPresent(), "El ticket no fue encontrado");

        // Imprimir el objeto Ticket si está presente
        result.ifPresent(ticket -> System.out.println("Ticket encontrado: " + ticket));
    }
}
