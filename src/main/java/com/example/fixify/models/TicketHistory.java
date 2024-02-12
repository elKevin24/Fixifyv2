package com.example.fixify.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "ticket_history")
public class TicketHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "technical_review", length = Integer.MAX_VALUE)
    private String technicalReview;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "update_timestamp")
    private Instant updateTimestamp;

    @Column(name = "change_type", length = 50)
    private String changeType;

}