package com.diner.tbrm.restaurante.diner.service.impl;

import com.diner.tbrm.restaurante.diner.dto.DinerRequestDTO;
import com.diner.tbrm.restaurante.diner.dto.DinerResponseDTO;
import com.diner.tbrm.restaurante.diner.model.Diner;
import com.diner.tbrm.restaurante.diner.model.mapper.DinerMapper;
import com.diner.tbrm.restaurante.diner.repository.DinerRepository;
import com.diner.tbrm.restaurante.diner.service.DinerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DinerServiceImpl implements DinerService {

    private final DinerRepository repository;
    private final DinerMapper mapper;

    @Override
    public List<DinerResponseDTO> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public DinerResponseDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElse(null);
    }

    @Override
    public DinerResponseDTO getByRun(String run) {
        return repository.findByRun(run)
                .map(mapper::toDto)
                .orElse(null);
    }

    @Override
    public DinerResponseDTO save(DinerRequestDTO dto) {
        Diner diner = mapper.toEntity(dto);
        return mapper.toDto(repository.save(diner));
    }

    @Override
    public DinerResponseDTO update(Long id, DinerRequestDTO dto) {
        Diner diner = repository.findById(id).orElse(null);

        if (diner == null) {
            return null;
        }

        diner.setRun(dto.getRun());
        diner.setName(dto.getName());
        diner.setLastName(dto.getLastName());
        diner.setPhone(dto.getPhone());
        diner.setAddress(dto.getAddress());
        diner.setEmail(dto.getEmail());
        diner.setBirthday(dto.getBirthday());

        return mapper.toDto(repository.save(diner));
    }

    @Override
    public Boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }

        return false;
    }
}