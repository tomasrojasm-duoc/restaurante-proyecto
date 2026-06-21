package com.notification.tbrm.restaurante.notification.service.impl;

import com.notification.tbrm.restaurante.notification.dto.NotificationChannelRequestDto;
import com.notification.tbrm.restaurante.notification.dto.NotificationChannelResponseDto;
import com.notification.tbrm.restaurante.notification.model.NotificationChannel;
import com.notification.tbrm.restaurante.notification.repository.NotificationChannelRepository;
import com.notification.tbrm.restaurante.notification.service.NotificationChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationChannelServiceImpl implements NotificationChannelService {

    private final NotificationChannelRepository repository;

    private NotificationChannelResponseDto toDto(NotificationChannel entity) {
        return new NotificationChannelResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus()
        );
    }

    private NotificationChannel toEntity(NotificationChannelRequestDto dto) {
        return new NotificationChannel(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getStatus()
        );
    }

    @Override
    public List<NotificationChannelResponseDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public NotificationChannelResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public NotificationChannelResponseDto findByName(String name) {
        NotificationChannel channel = repository.findByName(name);

        if (channel == null) {
            log.warn("Notification channel not found with name {}", name);
            return null;
        }

        return toDto(channel);
    }

    @Override
    public List<NotificationChannelResponseDto> findByStatus(String status) {
        return repository.findByStatus(status).stream().map(this::toDto).toList();
    }

    @Override
    public NotificationChannelResponseDto create(NotificationChannelRequestDto dto) {
        if (repository.findByName(dto.getName()) != null) {
            log.warn("Notification channel already exists with name {}", dto.getName());
            return null;
        }

        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public NotificationChannelResponseDto update(NotificationChannelRequestDto dto) {
        if (dto.getId() == null) {
            return null;
        }

        if (findById(dto.getId()) == null) {
            return null;
        }

        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public NotificationChannelResponseDto updateStatus(Long id, String status) {
        NotificationChannel channel = repository.findById(id).orElse(null);

        if (channel == null) {
            return null;
        }

        channel.setStatus(status);

        return toDto(repository.save(channel));
    }

    @Override
    public boolean delete(Long id) {
        if (findById(id) == null) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}