package com.example.fixify.models;

import com.example.fixify.models.catalogsDevice.Brand;
import com.example.fixify.models.catalogsDevice.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Data
@ToString
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "model")
    private String model;

    @Column(name = "series")
    private String series;

}