package com.example.fixify.service;


import com.example.fixify.models.Ticket;
import com.example.fixify.models.TicketStatus;
import com.example.fixify.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;



    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> findAllTickets() {

        return ticketRepository.findAll();
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
        List<Object[]> results = ticketRepository.countTicketsBySpecificStatusIds(statusIds);

        return results;
    }
}
