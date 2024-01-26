package com.example.fixify.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

import java.math.BigDecimal;
@ToString
@Data
@DynamicInsert
@Entity
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    private String Description;
    // Other properties like price, quantity, etc.

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private ServicesTicket servicio;

    // Getters and setters
}
