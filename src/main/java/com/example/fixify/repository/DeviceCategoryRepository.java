package com.example.fixify.repository;

import com.example.fixify.models.catalogsDevice.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceCategoryRepository extends JpaRepository<Category, Long> {
}