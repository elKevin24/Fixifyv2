package com.example.fixify.service;

import com.example.fixify.models.catalogsDevice.Brand;
import com.example.fixify.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }
}
