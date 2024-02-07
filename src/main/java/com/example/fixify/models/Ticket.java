package com.example.fixify.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Data
@NoArgsConstructor
@DynamicInsert
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "technical_review")
    private String technicalReview; // Descripción del resultado de la revisión técnica

    @ManyToOne
    @JoinColumn(name = "status_id")
    private TicketStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Technician technician;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;



    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicesTicket> servicios;

    @ManyToOne
    private Usuario createdBy;

    @ManyToOne
    private Usuario updatedBy;

}