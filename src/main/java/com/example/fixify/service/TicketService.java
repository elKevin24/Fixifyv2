package com.example.fixify.service;


import com.example.fixify.models.Ticket;
import com.example.fixify.models.TicketStatus;
import com.example.fixify.repository.TicketRepository;
import com.example.fixify.repository.TicketStatusRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketStatusRepository ticketStatusRepository;


    @Autowired
    public TicketService(TicketRepository ticketRepository, TicketStatusRepository ticketStatusRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketStatusRepository = ticketStatusRepository;
    }

    public Optional<Ticket> findOneById(Long id) {
        return ticketRepository.findById(id);
    }

    public List<Ticket> findAllTickets() {

        return ticketRepository.findAll();
    }

    public List<Ticket> findAllActiveTickets() {
        TicketStatus status = ticketStatusRepository.findById(0L)
                .orElseThrow(() -> new EntityNotFoundException("Estado de Ticket no encontrado con ID: " + 0L));
        // Filtra los tickets por estado diferente de cero
        return ticketRepository.findAllByStatusIsNot(status);
    }


    public Ticket saveTicket(Ticket ticket) {
        // Aquí podrías agregar lógica antes de guardar el ticket
        return ticketRepository.save(ticket);
    }

    public Optional<Ticket> findTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    public List<Object[]> countTicketsByStatus() {
        Set<Long> statusIds = new HashSet<>(Arrays.asList(1L, 2L, 5L, 7L));

        return ticketRepository.countTicketsBySpecificStatusIds(statusIds);
    }

    public void cambiarEstadoTicket(Long ticketId, Long statusId) {
        // Buscar el ticket
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado con ID: " + ticketId));

        // Buscar el estado del ticket
        TicketStatus nuevoEstado = ticketStatusRepository.findById(statusId)
                .orElseThrow(() -> new EntityNotFoundException("Estado de Ticket no encontrado con ID: " + statusId));

        // Actualizar el estado del ticket
        ticket.setStatus(nuevoEstado);

        // Guardar el ticket actualizado
        ticketRepository.save(ticket);
    }
}
