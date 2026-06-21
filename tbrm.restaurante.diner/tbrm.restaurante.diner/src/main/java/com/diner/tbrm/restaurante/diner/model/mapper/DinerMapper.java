package com.diner.tbrm.restaurante.diner.model.mapper;

import com.diner.tbrm.restaurante.diner.dto.DinerRequestDTO;
import com.diner.tbrm.restaurante.diner.dto.DinerResponseDTO;
import com.diner.tbrm.restaurante.diner.model.Diner;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DinerMapper {

    DinerMapper INSTANCE = Mappers.getMapper(DinerMapper.class);
    Diner toEntity(DinerRequestDTO dto);
    DinerResponseDTO toDto(Diner diner);
    List<DinerResponseDTO> toDtoList(List<Diner> list);
}