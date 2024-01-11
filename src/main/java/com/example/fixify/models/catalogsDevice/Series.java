package com.example.fixify.models.catalogsDevice;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "device_series")
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;


    // Constructores, getters y setters
}