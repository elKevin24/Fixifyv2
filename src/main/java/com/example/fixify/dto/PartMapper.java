package com.example.fixify.dto;

import com.example.fixify.models.Part;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PartMapper {

    PartMapper INSTANCE = Mappers.getMapper(PartMapper.class);

    TicketDto.ServicesTicketDto.PartDto toDto(Part part);

    Part fromDto(TicketDto.ServicesTicketDto.PartDto dto);
}
