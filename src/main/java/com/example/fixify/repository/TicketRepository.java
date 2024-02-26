package com.example.fixify.repository;


import com.example.fixify.models.Ticket;
import com.example.fixify.models.TicketStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByOrderByCreationDateDesc();

    @Query("SELECT t.status, COUNT(t) FROM Ticket t WHERE t.status.id IN :statusIds GROUP BY t.status ORDER BY t.status ASC")
    List<Object[]> countTicketsBySpecificStatusIds(@Param("statusIds") Set<Long> statusIds);

    List<Ticket> findAllByStatusIsNot(TicketStatus status);



}
