package com.example.fixify.service;

import com.example.fixify.models.Device;
import com.example.fixify.models.catalogsDevice.Brand;
import com.example.fixify.models.catalogsDevice.Category;
import com.example.fixify.repository.DeviceBrandRepository;
import com.example.fixify.repository.DeviceCategoryRepository;
import com.example.fixify.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    private DeviceBrandRepository deviceBrandRepository;

    @Autowired
    private DeviceCategoryRepository deviceCategoryRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> findAllDevices() {

        return deviceRepository.findAll();
    }
    
    public Device saveDevice(Device device) {
        // Aquí podrías agregar lógica antes de guardar el device
        return deviceRepository.save(device);
    }


    public List<Brand> getAllDeviceBrands() {
        return deviceBrandRepository.findAll();
    }

    public List<Category> getAllDeviceCategories() {
        return deviceCategoryRepository.findAll();
    }

}
