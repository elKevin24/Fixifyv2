package com.example.demo.service;

import com.example.demo.models.Device;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

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

    


    
}
