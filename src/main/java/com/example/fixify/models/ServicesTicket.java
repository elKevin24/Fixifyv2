package com.example.fixify.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@ToString
@DynamicInsert
@Data
@Entity
public class ServicesTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    private boolean esHardware;

    @ManyToOne
    @JoinColumn(name = "ticket_id_service")
    private Ticket ticket;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Part> parts;

}
