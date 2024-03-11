package com.example.fixify.dto;

import com.example.fixify.models.Ticket;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ServicesTicketMapper.class})
public interface TicketMapper {

    // Instancia para acceder al mapper sin necesidad de inyectarlo, útil si no usas Spring
    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);
    @Mapping(target = "createdName", source = "createdBy.username")
    @Mapping(target = "updatedName", source = "updatedBy.username")
    TicketDto toDto(Ticket ticket);

    @InheritInverseConfiguration
    Ticket fromDto(TicketDto dto);

    List<TicketDto> toDtos(List<Ticket> tickets);

    @InheritInverseConfiguration
    List<Ticket> toEntityList(List<TicketDto> tickets);
}