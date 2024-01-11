package com.example.fixify.models.catalogsDevice;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "device_brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    // Constructores, getters y setters
}
