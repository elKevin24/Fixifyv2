package com.example.fixify.models.catalogsDevice;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "device_categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)  // Agregando restricción de clave única aquí
    private String name;

    // Getters y setters
}