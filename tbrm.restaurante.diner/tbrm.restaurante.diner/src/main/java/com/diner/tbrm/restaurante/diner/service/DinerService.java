package com.diner.tbrm.restaurante.diner.service;

import com.diner.tbrm.restaurante.diner.dto.DinerRequestDTO;
import com.diner.tbrm.restaurante.diner.dto.DinerResponseDTO;

import java.util.List;

public interface DinerService {

    List<DinerResponseDTO> findAll();
    DinerResponseDTO getById(Long id);
    DinerResponseDTO getByRun(String run);
    DinerResponseDTO save(DinerRequestDTO dto);
    DinerResponseDTO update(Long id, DinerRequestDTO dto);
    Boolean deleteById(Long id);
}