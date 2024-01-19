package com.example.fixify.service;

import com.example.fixify.models.Ticket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TicketServiceIntegrationTest {

    @Autowired
    private TicketService ticketService;

    // Esta parte depende de cómo estés manejando la base de datos y los datos de prueba
    @BeforeEach
    public void setup() {
        // Preparar la base de datos con los datos necesarios para las pruebas
    }

    @AfterEach
    public void cleanup() {
        // Limpiar o restablecer la base de datos si es necesario
    }

    @Test
    public void testFindAllTickets() {
        List<Ticket> tickets = ticketService.findAllTickets();
        assertNotNull(tickets);
        // Puedes agregar más aserciones aquí para verificar los datos específicos de los tickets
    }

    @Test
    public void testSaveTicket() {
        Ticket newTicket = new Ticket(/* parametros para crear un ticket */);
//        Ticket savedTicket = ticketService.saveTicket(newTicket);
//        assertNotNull(savedTicket);
        // Verificar propiedades específicas del ticket guardado
    }

    @Test
    public void testFindTicketById() {
        // Asegúrate de que exista un ticket con este ID en tu base de datos de prueba
        Long ticketId = 1L;
        Optional<Ticket> ticket = ticketService.findTicketById(ticketId);
        assertTrue(ticket.isPresent());
        System.out.println("ticket = " + ticket);
        // Verificar propiedades específicas del ticket encontrado
    }

    @Test
    public void testCountTicketsByStatus() {
        List<Object[]> count = ticketService.countTicketsByStatus();
        assertNotNull(count);
        // Verificar el recuento y los detalles específicos según los datos de prueba
    }

    @Test
    public void testCambiarEstadoTicket() {
        Long ticketId = 1L; // Asegúrate de que este ticket exista en tu base de datos de prueba
        Long statusId = 0L;   // Asegúrate de que este estado exista en tu base de datos de prueba
        assertDoesNotThrow(() -> ticketService.cambiarEstadoTicket(ticketId, statusId));

        // Puedes realizar más verificaciones para asegurarte de que el estado del ticket ha cambiado
    }

    // Otros tests según sea necesario...
}
