package com.example.demo.service;

import com.example.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.demo.models.Ticket;

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




    // Aquí podrías agregar más métodos si necesitas realizar otras operaciones
}
