package com.example.fixify.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

import java.math.BigDecimal;
import java.util.List;

@ToString
@DynamicInsert
@Data
@Entity
@Table(name = "services_ticket")
public class ServicesTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private BigDecimal price;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Part> parts;


    @ManyToOne
    @JoinColumn(name = "ticket_id_service")
    private Ticket ticket;
}
