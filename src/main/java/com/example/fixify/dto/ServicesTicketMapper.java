package com.example.fixify.dto;

import com.example.fixify.models.ServicesTicket;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,  uses = {PartMapper.class})
public interface ServicesTicketMapper {

    ServicesTicketMapper INSTANCE = Mappers.getMapper(ServicesTicketMapper.class);

    TicketDto.ServicesTicketDto entityToDto(ServicesTicket entity);

    ServicesTicket dtoToEntity(TicketDto.ServicesTicketDto dto);
}