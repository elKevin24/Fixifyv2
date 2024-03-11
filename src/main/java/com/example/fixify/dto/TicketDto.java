package com.example.fixify.dto;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TicketDto implements Serializable {
    Long id;
    String description;
    String technicalReview;
    LocalDateTime creationDate;
    LocalDateTime lastModifiedDate;
    TicketStatusDto status;
    CustomerDto customer;
    TechnicianDto technician;
    DeviceDto device;
    List<ServicesTicketDto> servicios;
    String createdName;
    String updatedName;


    @Data
    public static class CustomerDto implements Serializable {
        String address;
        String email;
        String firstName;
        String lastName;
        String phone;
    }


    @Data
    public static class DeviceDto implements Serializable {
        Long id;
        String description;
        BrandDto brand;
        CategoryDto category;
        String model;
        String series;

        /**
         * DTO for {@link com.example.fixify.models.catalogsDevice.Brand}
         */
        @Data
        public static class BrandDto implements Serializable {
            String name;
        }

        /**
         * DTO for {@link com.example.fixify.models.catalogsDevice.Category}
         */
        @Data
        public static class CategoryDto implements Serializable {
            String name;
        }
    }

    @Data
    public static class ServicesTicketDto implements Serializable {
        private Long id;
        String description;
        BigDecimal price;
        List<PartDto> parts; // Lista de partes asociadas
        @Data
        public static class PartDto implements Serializable {
            Long id;
            String name;
            String description;
            BigDecimal price;
            // Otros campos según sea necesario
        }
    }


    @Data
    public static class TicketStatusDto implements Serializable {
        String name;
        String color;
    }

    @Data
    public static class TechnicianDto implements Serializable {
        String firstName;
        String lastName;
    }

    @Data
    public static class UsuarioDto implements Serializable {
        String username;
    }
}