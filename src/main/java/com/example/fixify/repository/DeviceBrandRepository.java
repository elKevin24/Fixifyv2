package com.example.fixify.repository;

import com.example.fixify.models.Device;
import com.example.fixify.models.catalogsDevice.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceBrandRepository extends JpaRepository<Brand, Long> {
}
