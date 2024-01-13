package com.example.fixify.repository;

import com.example.fixify.models.catalogsDevice.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}